import React from 'react';
import Main from "./pages/Main";
import {Route, Routes} from "react-router-dom";
import News from "./pages/News";
import Shop from "./pages/Shop";
import Login from "./pages/Login";
import Registration from "./pages/Registration";
import Layout from "./components/Layout";
import User from "./pages/User";
import PrivateRoute from "./routes/PrivateRoute";
import Panel from "./pages/Panel";
import PanelRoute from "./routes/PanelRoute";

function App() {
  return (
      <Routes>
        <Route path="/" element={<Layout/>}>
            <Route index element={<Main />} />
            <Route path="/news" element={<News/>}/>
            <Route path="/shop" element={<Shop/>}/>
            <Route path="/login" element={<Login/>}/>
            <Route path="/registration" element={<Registration/>}/>
            <Route path="/users/:param" element={
                <PrivateRoute>
                    <User/>
                </PrivateRoute>}
            />
            <Route path="/panel/:param" element={
                <PanelRoute>
                    <Panel />
                </PanelRoute>
            }/>
        </Route>
      </Routes>
  );
}

export default App;
