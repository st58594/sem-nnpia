import './App.css'
import ReactConcept from "./components/ReactConcept.jsx";
import {data} from "./assets/init-data.js";

function App() {

    return (
        <>
            <div>
                {
                    data.map((value, index) => (
                            <ReactConcept key={index} description={value.description} title={value.title} image={value.image}></ReactConcept>
                        )
                    )
                }
            </div>
        </>
    )
}

export default App
