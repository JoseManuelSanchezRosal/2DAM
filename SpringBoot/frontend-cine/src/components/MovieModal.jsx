import { useState, useEffect, useRef } from 'react';
import axios from 'axios'; 
import { X, Play, Plus, Check, ThumbsUp, ArrowLeft, Star, MessageSquare, Clock, Calendar, Send } from 'lucide-react';

const MovieModal = ({ movie, onClose, onToggleMyList, isFavorite }) => {
  const [showTrailer, setShowTrailer] = useState(false);
  const [reviews, setReviews] = useState(movie.criticas || []);
  const [newComment, setNewComment] = useState("");
  const [newRating, setNewRating] = useState(5);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const dataFetched = useRef(false);
  const scrollRef = useRef(null);

  useEffect(() => {
      if (dataFetched.current) return;
      axios.get(`http://localhost:8081/api/peliculas/${movie.id}`)
          .then(response => {
              if (response.data.criticas) setReviews(response.data.criticas);
              dataFetched.current = true;
          })
          .catch(error => console.error("Error refrescando datos:", error));
      return () => { dataFetched.current = false; };
  }, [movie.id]);

  // Auto-scroll al fondo cuando se añade un comentario (opcional)
  useEffect(() => {
    if(scrollRef.current) {
        scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
    }
  }, [reviews]);

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
          console.error(error);
      } finally {
          setIsSubmitting(false);
      }
  };

  if (!movie) return null;

  return (
    // LAYOUT PRINCIPAL: Fixed, sin scroll en el body
    <div className="fixed inset-0 z-50 bg-[#0a0a0a] flex flex-row overflow-hidden font-sans text-white animate-fade-in">
      
      {/* BOTÓN CERRAR (Flotante global) */}
      <button 
        onClick={onClose}
        className="absolute top-4 right-4 z-[60] text-gray-400 hover:text-white hover:rotate-90 transition duration-300"
      >
        <X size={32} />
      </button>

      {/* --- IZQUIERDA: HERO SECTION (70%) --- */}
      <div className="w-[70%] h-full relative group">
        
        {/* FONDO: VIDEO O IMAGEN QUE OCUPA TODO EL ALTO */}
        <div className="absolute inset-0 w-full h-full">
            {showTrailer && movie.trailerKey ? (
                <iframe 
                    className="w-full h-full object-cover"
                    src={`https://www.youtube.com/embed/${movie.trailerKey}?autoplay=1&controls=0&modestbranding=1&rel=0`} 
                    title="Trailer" 
                    allow="autoplay; encrypted-media" 
                ></iframe>
            ) : (
                <img 
                    src={`https://image.tmdb.org/t/p/original${movie.backdropPath}`} 
                    alt={movie.titulo}
                    className="w-full h-full object-cover opacity-90"
                />
            )}
            {/* DEGRADADO INTEGRADO (Vignette) para leer el texto */}
            <div className={`absolute inset-0 bg-gradient-to-t from-[#0a0a0a] via-[#0a0a0a]/40 to-transparent ${showTrailer ? 'opacity-0 hover:opacity-100 transition-opacity duration-500' : ''}`}></div>
            <div className="absolute inset-0 bg-gradient-to-r from-[#0a0a0a]/80 via-transparent to-transparent"></div>
        </div>

        {/* CONTENIDO INFO (Flotando sobre la imagen abajo a la izquierda) */}
        <div className={`absolute bottom-0 left-0 p-12 w-full max-w-4xl z-20 ${showTrailer ? 'opacity-0 hover:opacity-100 transition-opacity duration-500' : ''}`}>
             
             {/* Metadata Chips */}
             <div className="flex items-center gap-3 mb-4 text-sm font-semibold tracking-wide">
                <span className="text-[#E5A909] flex items-center gap-1"><Star size={16} fill="currentColor"/> {movie.valoracion}</span>
                <span className="text-gray-300">{movie.fechaEstreno?.split('-')[0]}</span>
                <span className="bg-white/20 px-2 py-0.5 rounded text-xs backdrop-blur-sm">HD</span>
                <span className="text-gray-300">{movie.duracion} min</span>
             </div>

             <h1 className="text-5xl md:text-7xl font-black mb-6 leading-none tracking-tight drop-shadow-xl">
                {movie.titulo}
             </h1>

             <p className="text-gray-200 text-lg mb-8 line-clamp-3 max-w-2xl drop-shadow-md">
                {movie.sinopsis}
             </p>

             {/* Botonera Principal */}
             <div className="flex items-center gap-4">
                {movie.trailerKey && !showTrailer ? (
                    <button 
                        onClick={() => setShowTrailer(true)}
                        className="bg-white text-black px-8 py-3 rounded-lg font-bold hover:bg-[#E5A909] transition flex items-center gap-2"
                    >
                        <Play fill="black" size={20} /> Ver Trailer
                    </button>
                ) : showTrailer && (
                    <button 
                        onClick={() => setShowTrailer(false)}
                        className="bg-white/20 backdrop-blur-md text-white px-6 py-3 rounded-lg font-bold hover:bg-white/40 transition flex items-center gap-2 border border-white/30"
                    >
                        <ArrowLeft size={20} /> Volver
                    </button>
                )}

                <button 
                    onClick={() => onToggleMyList && onToggleMyList(movie)}
                    className="px-6 py-3 rounded-lg font-bold border-2 border-gray-500 text-gray-300 hover:border-white hover:text-white transition flex items-center gap-2 bg-black/30 backdrop-blur-sm"
                >
                    {isFavorite ? <Check size={20} /> : <Plus size={20} />}
                    Mi Lista
                </button>
                
                <button className="p-3 rounded-full border-2 border-gray-500 text-gray-300 hover:border-white hover:text-white transition bg-black/30 backdrop-blur-sm">
                    <ThumbsUp size={20} />
                </button>
             </div>
        </div>
      </div>

      {/* --- DERECHA: SIDEBAR DE CRÍTICAS (30%) --- */}
      <div className="w-[30%] bg-[#121212] border-l border-gray-800 flex flex-col h-full shadow-2xl z-20">
          
          {/* Header Sidebar */}
          <div className="p-5 border-b border-white/10 flex justify-between items-center bg-[#161616]">
              <h3 className="font-bold text-lg flex items-center gap-2">
                  Opiniones <span className="text-[#E5A909] text-sm">({reviews.length})</span>
              </h3>
          </div>

          {/* Lista de Reviews (Scrollable solo aquí) */}
          <div ref={scrollRef} className="flex-1 overflow-y-auto p-4 space-y-3 custom-scrollbar">
              {reviews.length > 0 ? (
                  reviews.map((review, index) => (
                      <div key={index} className="bg-[#1f1f1f] p-3 rounded-lg border border-white/5 hover:border-white/20 transition group">
                          <div className="flex justify-between items-start mb-1">
                              <span className="font-bold text-gray-200 text-sm">{review.nombreUsuario || "Anónimo"}</span>
                              <div className="flex text-[#E5A909] text-xs gap-0.5">
                                  <Star size={12} fill="currentColor"/>
                                  <span className="font-mono">{review.nota}</span>
                              </div>
                          </div>
                          <p className="text-gray-400 text-xs leading-relaxed group-hover:text-gray-300 transition-colors">
                            "{review.comentario}"
                          </p>
                      </div>
                  ))
              ) : (
                  <div className="h-full flex flex-col items-center justify-center text-gray-600 opacity-60">
                      <MessageSquare size={40} className="mb-2"/>
                      <p className="text-sm">Sin comentarios aún</p>
                  </div>
              )}
          </div>

          {/* Input Compacto Fijo Abajo */}
          <div className="p-4 bg-[#161616] border-t border-white/10">
              <div className="flex justify-between mb-2">
                 <span className="text-[10px] uppercase text-gray-500 font-bold tracking-wider">Tu Valoración</span>
                 <div className="flex gap-1">
                    {[...Array(10)].map((_, i) => (
                        <Star 
                            key={i} 
                            size={12} 
                            className={`cursor-pointer ${i < newRating ? 'text-[#E5A909] fill-[#E5A909]' : 'text-gray-700'}`}
                            onClick={() => setNewRating(i + 1)}
                        />
                    ))}
                 </div>
              </div>
              
              <form onSubmit={handleSubmitReview} className="relative">
                  <textarea 
                      className="w-full bg-[#0a0a0a] text-gray-200 text-sm rounded-lg p-3 pr-10 border border-gray-700 focus:border-[#E5A909] focus:outline-none resize-none h-14"
                      placeholder="Escribe algo..."
                      value={newComment}
                      onChange={(e) => setNewComment(e.target.value)}
                  ></textarea>
                  <button 
                    type="submit" 
                    disabled={!newComment.trim() || isSubmitting}
                    className="absolute right-2 top-2 p-1.5 bg-[#E5A909] text-black rounded hover:bg-white transition disabled:opacity-50"
                  >
                    <Send size={14} />
                  </button>
              </form>
          </div>
      </div>

    </div>
  );
};

export default MovieModal;