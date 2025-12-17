import React from 'react';

const IMAGE_URL = "https://image.tmdb.org/t/p/w500"; 

function MovieGrid({ title, movieList, emptyMessage, handleMovieClick, changeView }) {
    return (
        <div className="pt-32 px-4 md:px-12 min-h-[70vh] animate-fade-in">
            
            <style>{`
                .gold-glow-grid {
                    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.5);
                    transition: all 0.3s ease-in-out;
                }
                .group:hover .gold-glow-grid {
                    box-shadow: 0px 0px 30px 5px rgba(229, 169, 9, 0.6) !important;
                }
            `}</style>

            {/* Quitamos text-white para que herede el color del tema (Día/Noche) */}
            <h2 className="text-3xl font-bold mb-8 border-l-4 border-[#E5A909] pl-4">{title}</h2>

            {movieList.length > 0 ? (
                <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-5 gap-6">
                    {movieList.map(movie => (
                        <div
                            key={movie.id}
                            onClick={() => handleMovieClick && handleMovieClick(movie)}
                            className="relative group cursor-pointer transition-all duration-300 hover:scale-105 hover:z-50"
                        >
                            <img
                                src={movie.posterPath ? `${IMAGE_URL}${movie.posterPath}` : "https://via.placeholder.com/500x750"}
                                alt={movie.titulo}
                                // CAMBIO 1: Mismas esquinas personalizadas que en MovieRow
                                className="rounded-tl-3xl rounded-br-3xl rounded-tr-md rounded-bl-md w-full h-auto object-cover aspect-[2/3] gold-glow-grid"
                            />
                            
                            {/* CAMBIO 2: Overlay ajustado a la forma de la imagen */}
                            <div className="absolute bottom-0 left-0 right-0 p-4 bg-gradient-to-t from-black via-black/80 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300 rounded-br-3xl rounded-bl-md flex flex-col justify-end">
                                <h3 className="text-sm font-bold text-white leading-tight truncate">{movie.titulo}</h3>
                                <div className="flex items-center gap-2 text-xs text-[#E5A909] mt-1 font-bold">
                                    <span>★ {movie.valoracion} / 10</span>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            ) : (
                <div className="flex flex-col items-center justify-center mt-20 text-gray-500 h-[40vh]">
                    <div className="text-6xl mb-4 text-[#E5A909]">☹️</div>
                    <p className="text-xl">{emptyMessage}</p>
                    <button onClick={() => changeView && changeView('home')} className="mt-6 bg-white text-black px-6 py-2 rounded font-bold hover:bg-[#E5A909] hover:text-white transition">
                        Volver al Inicio
                    </button>
                </div>
            )}
        </div>
    );
}

export default MovieGrid;