import React from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/navigation';
import { Star } from 'lucide-react';

const IMAGE_URL = "https://image.tmdb.org/t/p/w500";

// Recibimos onMovieClick como prop
const MovieRow = ({ title, movies, onMovieClick }) => {
  return (
    <div className="mb-8 px-4 md:px-10 group/row">
      <h2 className="text-xl md:text-2xl font-bold mb-4 text-white hover:text-[#E50914] transition-colors cursor-pointer inline-block">
        {title}
      </h2>

      <Swiper
        modules={[Navigation]}
        navigation
        spaceBetween={10}
        slidesPerView={2}
        breakpoints={{
            640: { slidesPerView: 3, spaceBetween: 20 },
            768: { slidesPerView: 4, spaceBetween: 25 },
            1024: { slidesPerView: 6, spaceBetween: 15 },
        }}
        className="py-4 !overflow-visible" 
      >
        {movies.map((movie) => (
          <SwiperSlide key={movie.id}>
            {/* AÑADIDO: onClick={() => onMovieClick(movie)} */}
            <div 
              onClick={() => onMovieClick && onMovieClick(movie)}
              className="relative group cursor-pointer transition-all duration-300 hover:scale-110 hover:z-50"
            >
              <img 
                src={movie.posterPath ? `${IMAGE_URL}${movie.posterPath}` : "https://via.placeholder.com/500x750"} 
                alt={movie.titulo}
                className="rounded-md w-full h-auto object-cover shadow-black/50 shadow-lg"
              />
              <div className="absolute bottom-0 left-0 right-0 p-2 bg-gradient-to-t from-black via-black/80 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300 rounded-b-md">
                <h3 className="text-xs font-bold text-white truncate">{movie.titulo}</h3>
                <div className="flex items-center gap-1 mt-1">
                    <Star size={10} className="text-yellow-400 fill-yellow-400"/>
                    <span className="text-[10px] text-green-400 font-bold">{movie.valoracion} Puntos</span>
                </div>
              </div>
            </div>
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  );
};

export default MovieRow;