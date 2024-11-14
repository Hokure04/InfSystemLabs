import React from "react"

const videos = [
    {
        id: 1,
        name: "Замыкания от и до",
        duration: 15,
    },
    {
        id: 2,
        name: "Роадмэп по JS",
        duration: 15,
    },
    {
        id: 3,
        name: "Что такое фронтенд",
        duration: 15,
    },
    {
        id: 4,
        name: "Зачем и почему?",
        duration: 20,
    }
]

export function VideoList(){
    return(
        <>
            <h1>Video List</h1>
            {
                videos.map((video) =>{
                    return(
                        <div key={video.id}>
                            <p>{video.name}</p>
                            <p>{video.duration}</p>
                            <button>Like</button>
                        </div>
                    )
                } )
            }
        </>
    )
}