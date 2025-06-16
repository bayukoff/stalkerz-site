import {Canvas, useLoader} from "@react-three/fiber";
import {MathUtils, Mesh, MeshStandardMaterial, NearestFilter, TextureLoader} from "three";
import {OBJLoader} from "three/examples/jsm/loaders/OBJLoader";
import {Suspense, useEffect, useMemo} from "react";

const UserAvatarComponent = () => {

    /*
    0 - steve texture
    1 - bg texture
     */
    const textures = useLoader(TextureLoader, ["http://localhost:8080/assets/skin/gold_layer_1.png", "https://i.postimg.cc/m2p9Tyw7/2020-12-23-13-11-43.png"])
    const processedTexture = useMemo(() =>{
        return textures.map(tex => {
            tex.minFilter = NearestFilter
            tex.magFilter = NearestFilter
            return tex
        })
    }, [textures])
    const SteveModel = () => {

        const steveModel = useLoader(OBJLoader, "http://localhost:8080/assets/model/steve.obj")

        const processedSteveModel = useMemo(() => {
            steveModel.traverse(child  =>
                (child as Mesh).material = new MeshStandardMaterial({map: processedTexture[0]})
            )
            return steveModel
        }, [steveModel]);

        return <primitive object={processedSteveModel} position={[0, -4.8, 0.5]} rotation={[0, MathUtils.degToRad(20), 0]}/>
    }



    return (
        <div className="user_avatar">
            <Canvas camera=
                        {
                            {
                                fov: 60,
                                far: 50,
                                near: 0.01,
                                isPerspectiveCamera: true
                            }
                        }

            >
                <pointLight position={[0,0,0]} intensity={20}>

                </pointLight>
                <ambientLight color={"white"} intensity={1}/>
                <mesh scale={[15,10,10]}>
                    <planeGeometry attach="geometry" args={[1,1]}/>
                    <meshStandardMaterial map={textures[1]}/>
                </mesh>
                <Suspense>
                    <SteveModel/>
                </Suspense>
            </Canvas>
        </div>
    )
}

export default UserAvatarComponent