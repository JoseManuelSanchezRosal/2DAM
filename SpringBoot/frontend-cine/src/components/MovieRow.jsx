import React from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/navigation';
import { Star } from 'lucide-react';

const IMAGE_URL = "https://image.tmdb.org/t/p/w500";

const MovieRow = ({ title, movies, onMovieClick }) => {
  return (
    <div className="mb-10 px-4 md:px-10 group/row">
      
      <style>{`
        .gold-glow {
          box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.5);
          transition: all 0.3s ease-in-out;
        }
        .group:hover .gold-glow {
          box-shadow: 0px 0px 30px 5px rgba(229, 169, 9, 0.6) !important;
        }
      `}</style>
      
      {/* Título centrado */}
      <div className="w-full flex justify-center mb-7">
          <h2 className="
              text-xl md:text-2xl font-bold 
              transition-all duration-300 cursor-pointer inline-block 
              origin-center
              group-hover/row:text-[#E5A909] 
              group-hover/row:scale-110
          ">
            {title}
          </h2>
      </div>

      <Swiper
        style={{
          "--swiper-navigation-color": "#E5A909", 
          "--swiper-pagination-color": "#E5A909"
        }}
        modules={[Navigation]}
        navigation
        spaceBetween={10}
        slidesPerView={2}
        breakpoints={{
            640: { slidesPerView: 3, spaceBetween: 20 },
            768: { slidesPerView: 4, spaceBetween: 25 },
            1024: { slidesPerView: 6, spaceBetween: 15 },
        }}
        className="py-8 !overflow-visible" 
      >
        {movies.map((movie) => (
          <SwiperSlide key={movie.id}>
            <div 
              onClick={() => onMovieClick && onMovieClick(movie)}
              className="relative group cursor-pointer transition-all duration-300 hover:scale-110 hover:z-50"
            >
              <img 
                src={movie.posterPath ? `${IMAGE_URL}${movie.posterPath}` : "https://via.placeholder.com/500x750"} 
                alt={movie.titulo}
                // CAMBIO 1: Esquinas personalizadas (Top-Left y Bottom-Right exageradas)
                className="rounded-tl-3xl rounded-br-3xl rounded-tr-md rounded-bl-md w-full h-auto object-cover gold-glow"
              />
              
              {/* CAMBIO 2: Ajustamos el borde inferior derecho del overlay para que coincida con la foto */}
              <div className="absolute bottom-0 left-0 right-0 p-2 bg-gradient-to-t from-black via-black/80 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300 rounded-br-3xl rounded-bl-md">
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