function MovieGrid({ title, movieList, emptyMessage }) {
    return (
        <div className="pt-32 px-4 md:px-12 min-h-[70vh] animate-fade-in">
            <h2 className="text-3xl font-bold text-white mb-8 border-l-4 border-[#E5A909] pl-4">{title}</h2>

            {movieList.length > 0 ? (
                <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-5 gap-6">
                    {movieList.map(movie => (
                        <div
                            key={movie.id}
                            onClick={() => handleMovieClick(movie)}
                            className="relative group cursor-pointer transition-all duration-300 hover:scale-105 hover:z-50"
                        >
                            <img
                                src={movie.posterPath ? `${IMAGE_URL}${movie.posterPath}` : "https://via.placeholder.com/500x750"}
                                alt={movie.titulo}
                                className="rounded-md w-full h-auto object-cover shadow-lg aspect-[2/3] group-hover:ring-2 group-hover:ring-[#E5A909]"
                            />
                            <div className="absolute inset-0 bg-black/60 opacity-0 group-hover:opacity-100 transition-opacity flex flex-col justify-end p-4 rounded-md">
                                <h3 className="text-sm font-bold text-white">{movie.titulo}</h3>
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
                    <button onClick={() => changeView('home')} className="mt-6 bg-white text-black px-6 py-2 rounded font-bold hover:bg-[#E5A909] hover:text-white transition">
                        Volver al Inicio
                    </button>
                </div>
            )}
        </div>
    );
}

export default MovieGrid;