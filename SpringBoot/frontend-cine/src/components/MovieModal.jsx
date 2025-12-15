import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios'; 
import { X, Play, Plus, Check, ThumbsUp, ArrowLeft, Star, MessageSquare } from 'lucide-react';

const MovieModal = ({ movie, onClose, onToggleMyList, isFavorite }) => {
  const [showTrailer, setShowTrailer] = useState(false);
  // Inicializamos con las críticas que vienen de props
  const [reviews, setReviews] = useState(movie.criticas || []);
  const [newComment, setNewComment] = useState("");
  const [newRating, setNewRating] = useState(5);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const dataFetched = useRef(false);

  // Efecto para cargar críticas frescas de la BBDD una sola vez
  useEffect(() => {
      if (dataFetched.current) return;
      
      axios.get(`http://localhost:8081/api/peliculas/${movie.id}`)
          .then(response => {
              if (response.data.criticas) {
                  setReviews(response.data.criticas);
              }
              dataFetched.current = true;
          })
          .catch(error => console.error("Error refrescando datos:", error));
      
      return () => { dataFetched.current = false; };
  }, [movie.id]);

  const handleSubmitReview = async (e) => {
      e.preventDefault();
      if (!newComment.trim()) return;

      setIsSubmitting(true);
      try {
          const payload = {
              comentario: newComment,
              nota: newRating,
              fecha: new Date().toLocaleDateString('es-ES') 
          };
          const response = await axios.post(`http://localhost:8081/api/criticas/${movie.id}`, payload);
          
          setReviews([...reviews, response.data]);
          setNewComment("");
          setNewRating(5);
      } catch (error) {
          console.error("Error enviando crítica:", error);
          alert("Hubo un error al guardar tu crítica.");
      } finally {
          setIsSubmitting(false);
      }
  };

  if (!movie) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/80 backdrop-blur-sm p-4 overflow-y-auto" onClick={onClose}>
      
      <div 
        className="relative w-full max-w-5xl bg-[#181818] rounded-xl shadow-2xl overflow-hidden animate-fade-in-up border border-gray-800 flex flex-col max-h-[90vh]" 
        onClick={(e) => e.stopPropagation()}
      >
        <button 
          onClick={onClose}
          className="absolute top-4 right-4 z-30 bg-[#181818]/80 rounded-full p-2 hover:bg-gray-700 transition"
        >
          <X className="text-white" size={24} />
        </button>

        <div className="overflow-y-auto custom-scrollbar flex-1">
            {/* ZONA SUPERIOR: BANNER / VIDEO */}
            <div className="relative w-full h-[350px] md:h-[450px] bg-black group shrink-0">
                {showTrailer && movie.trailerKey ? (
                    <iframe 
                        className="w-full h-full"
                        src={`https://www.youtube.com/embed/${movie.trailerKey}?autoplay=1&rel=0&modestbranding=1`} 
                        title="YouTube video player" 
                        frameBorder="0" 
                        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                        allowFullScreen
                    ></iframe>
                ) : (
                    <>
                        <img 
                            src={`https://image.tmdb.org/t/p/original${movie.backdropPath}`} 
                            alt={movie.titulo}
                            className="w-full h-full object-cover opacity-80"
                        />
                        <div className="absolute inset-0 bg-gradient-to-t from-[#181818] via-transparent to-transparent"></div>
                        
                        {/* Botón Play Gigante (Estilo Gold) */}
                        {movie.trailerKey && (
                            <button 
                                 onClick={() => setShowTrailer(true)}
                                 className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-[#E5A909]/90 text-black p-5 rounded-full hover:bg-yellow-500 hover:scale-110 transition shadow-lg shadow-yellow-900/50"
                            >
                                <Play fill="black" size={32} />
                            </button>
                        )}

                        <div className="absolute bottom-8 left-8 max-w-xl">
                            <h2 className="text-4xl md:text-5xl font-extrabold text-white mb-4 drop-shadow-xl leading-tight">{movie.titulo}</h2>
                            
                            <div className="flex gap-3">
                                {movie.trailerKey ? (
                                    <button 
                                        onClick={() => setShowTrailer(true)} 
                                        className="flex items-center gap-2 bg-white text-black px-6 py-2 rounded font-bold hover:bg-[#E5A909] transition text-md"
                                    >
                                        <Play fill="black" size={20} /> Ver Trailer
                                    </button>
                                ) : (
                                    <button className="bg-gray-600 text-white px-6 py-2 rounded cursor-not-allowed font-bold opacity-50 text-sm">Trailer no disponible</button>
                                )}

                                {/* Botón Mi Lista (Check Dorado si es favorito) */}
                                <button 
                                    onClick={() => onToggleMyList && onToggleMyList(movie)}
                                    className={`flex items-center justify-center w-10 h-10 border-2 rounded-full transition 
                                        ${isFavorite 
                                            ? 'border-[#E5A909] text-[#E5A909] hover:bg-[#E5A909]/20' 
                                            : 'border-gray-400 text-gray-300 hover:border-white hover:text-white'
                                        }`}
                                >
                                    {isFavorite ? <Check size={20} /> : <Plus size={20} />}
                                </button>
                                <button className="flex items-center justify-center w-10 h-10 border-2 border-gray-400 rounded-full text-gray-300 hover:border-white hover:text-white transition">
                                    <ThumbsUp size={20} />
                                </button>
                            </div>
                        </div>
                    </>
                )}
                
                {showTrailer && (
                    <button onClick={() => setShowTrailer(false)} className="absolute top-4 left-4 z-30 flex items-center gap-2 bg-black/60 text-white px-3 py-1 rounded hover:bg-black/80 transition backdrop-blur-md text-sm">
                        <ArrowLeft size={16} /> Volver
                    </button>
                )}
            </div>

            {/* ZONA INFERIOR: INFO Y CRÍTICAS */}
            <div className="p-8 md:p-12 space-y-10 bg-[#181818]">
                <div className="flex flex-col md:flex-row gap-12">
                    <div className="flex-1 text-white">
                        <div className="flex items-center gap-4 mb-6 text-sm font-bold">
                            <span className="text-[#46d369]">98% de coincidencia</span>
                            <span>{movie.fechaEstreno ? movie.fechaEstreno.split('-')[0] : 'N/A'}</span>
                            <span className="border border-gray-500 px-1 rounded text-xs text-gray-400">HD</span>
                        </div>
                        <p className="text-gray-300 text-lg leading-relaxed">{movie.sinopsis}</p>
                    </div>
                    <div className="w-full md:w-1/3 text-gray-400 text-sm space-y-4">
                        <div><span className="text-gray-500 block mb-1">Valoración:</span> <span className="text-white font-bold text-lg">{movie.valoracion} / 10</span></div>
                        <div><span className="text-gray-500 block mb-1">Duración:</span> <span className="text-white">{movie.duracion} min</span></div>
                    </div>
                </div>

                <hr className="border-gray-800" />

                <div className="space-y-6">
                    <h3 className="text-2xl font-bold text-white flex items-center gap-2">
                        <MessageSquare className="text-[#E5A909]" /> Opiniones de la comunidad
                    </h3>

                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4 max-h-[400px] overflow-y-auto pr-2 custom-scrollbar">
                        {reviews.length > 0 ? (
                            reviews.map((review, index) => (
                                <div key={review.id || index} className="bg-[#2f2f2f] p-4 rounded-lg border border-gray-700 h-fit">
                                    <div className="flex justify-between items-start mb-2">
                                        <span className="font-bold text-gray-200">{review.nombreUsuario || "Anónimo"}</span>
                                        <div className="flex text-yellow-500 text-sm">
                                            {[...Array(5)].map((_, i) => (
                                                <Star key={i} size={14} fill={i < (review.nota / 2) ? "currentColor" : "none"} />
                                            ))}
                                            <span className="ml-2 text-gray-400">{review.nota}/10</span>
                                        </div>
                                    </div>
                                    <p className="text-gray-300 text-sm italic">"{review.comentario}"</p>
                                    <span className="text-xs text-gray-500 mt-2 block text-right">{review.fecha}</span>
                                </div>
                            ))
                        ) : (
                            <p className="text-gray-500 italic col-span-2">Todavía no hay opiniones. ¡Sé el primero!</p>
                        )}
                    </div>

                    <div className="bg-[#1f1f1f] p-6 rounded-lg border border-gray-700 mt-6">
                        <h4 className="text-lg font-bold text-white mb-4">Escribe tu reseña</h4>
                        <form onSubmit={handleSubmitReview} className="space-y-4">
                            <div className="flex items-center gap-4">
                                <label className="text-gray-400 text-sm">Tu nota (0-10):</label>
                                <div className="flex items-center gap-2 bg-[#2f2f2f] px-3 py-1 rounded border border-gray-600">
                                    <input 
                                        type="number" min="0" max="10" 
                                        value={newRating}
                                        onChange={(e) => setNewRating(parseInt(e.target.value))}
                                        className="bg-transparent text-white w-8 focus:outline-none font-bold text-center"
                                    />
                                    <span className="text-gray-500">/ 10</span>
                                </div>
                                <div className="flex text-yellow-500">
                                    {[...Array(5)].map((_, i) => (
                                        <Star key={i} size={18} fill={i < (newRating / 2) ? "currentColor" : "none"} />
                                    ))}
                                </div>
                            </div>

                            <textarea 
                                className="w-full bg-[#2f2f2f] text-white border border-gray-600 rounded p-3 focus:outline-none focus:border-[#E5A909] text-sm"
                                rows="3"
                                placeholder="¿Qué te ha parecido esta película? (Máx 2500 caracteres)"
                                value={newComment}
                                onChange={(e) => setNewComment(e.target.value)}
                                maxLength={2500}
                            ></textarea>

                            <div className="flex justify-end">
                                <button 
                                    type="submit" 
                                    disabled={isSubmitting || !newComment.trim()}
                                    className="bg-[#E5A909] text-black px-6 py-2 rounded font-bold hover:bg-yellow-500 transition disabled:opacity-50 disabled:cursor-not-allowed"
                                >
                                    {isSubmitting ? "Enviando..." : "Publicar reseña"}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
      </div>
    </div>
  );
};

export default MovieModal;