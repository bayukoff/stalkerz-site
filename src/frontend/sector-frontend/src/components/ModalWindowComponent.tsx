import {motion} from "framer-motion"


const ModalWindowComponent = ({children}: any) => {

    const variants = {
        hidden: {
            opacity: 0,
            scale: 0.2, x: "-50%"
        },
        half: {
            opacity: 1,
            scale: 1, x: "-50%",
            transition: {
                opacity: {duration: 0.2},
                scale: {duration: 0.3, ease: [.28,.71,.81,1.34] as [number, number, number, number]}
            }
        },
        exit: {
            scale: 0, x: "-50%",
            transition: {
                opacity: {duration: 0.2},
                translate: {duration: 0.3, ease: [0,-0.44,.46,.49] as [number, number, number, number]}
            }
        }
    }

    return (
        <motion.div layout key="modal" variants={variants} initial="hidden" animate="half" exit="exit" className="modal__window">
            {children}
        </motion.div>
    )
}

export default ModalWindowComponent