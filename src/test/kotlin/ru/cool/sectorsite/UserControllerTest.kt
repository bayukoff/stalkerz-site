package ru.cool.sectorsite

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.cool.sectorsite.controller.UserController
import ru.cool.sectorsite.dto.UserDto
import ru.cool.sectorsite.model.User
import ru.cool.sectorsite.service.UserService

@ExtendWith(MockitoExtension::class)
class UserControllerTest {

    @Mock
    private lateinit var userService: UserService

    @InjectMocks
    private lateinit var userController: UserController

    private lateinit var objectMapper: ObjectMapper

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setupMockMvc(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build()
        objectMapper = Jackson2ObjectMapperBuilder.json().build()
    }

    @Test
    fun `test get all users`(){
        val users = listOf(User("Coolik", "!Cool2929224", "coolik15128@cool.cool"), User("Coolik124", "!Cool29292212414", "coolik15128@cool.cool"))
        whenever(userService.getAll()).thenReturn(users)
        mockMvc.get("/api/users").andExpect {
            status { isOk() }
            content { jsonPath("$", hasSize<Int>(2)) }
        }
    }

    @Test
    fun `test create user when data correct`(){
        val user = UserDto("Coolz", "aboba@test.com", "!Dc69656665")
        mockMvc
            .post("/api/users/new") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(user)
            }
            .andExpect {
                status {
                    isOk()
                }
            }
    }

    @Test
    fun `test create user when data incorrect`(){
        val user = UserDto("1", "aboba@test.com", "123")
        mockMvc
            .post("/api/users/new") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(user)
            }
            .andExpect {
                status {
                    isBadRequest()
                }
            }
    }
}