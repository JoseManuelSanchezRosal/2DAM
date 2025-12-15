import { useState, useEffect } from 'react'
import axios from 'axios'
// AÑADIDO: 'User' a los imports
import { Play, Info, Search, Bell, X, Tv, Home, Film, Bookmark, Facebook, Twitter, Instagram, User } from 'lucide-react'
import { Swiper, SwiperSlide } from 'swiper/react';
import { Autoplay, EffectFade } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/effect-fade';

import MovieRow from './components/MovieRow'
import MovieModal from './components/MovieModal'

const IMAGE_URL = "https://image.tmdb.org/t/p/w500"; 
const GOLD_COLOR = "#E5A909"; 

function App() {
  const [movies, setMovies] = useState([])
  const [isScrolled, setIsScrolled] = useState(false);
  const [selectedMovie, setSelectedMovie] = useState(null);
  
  const [isSearchOpen, setIsSearchOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [view, setView] = useState('home');

  const [myList, setMyList] = useState(() => {
    const saved = localStorage.getItem("yosefhflix_mylist");
    return saved ? JSON.parse(saved) : [];
  });

  useEffect(() => {
    localStorage.setItem("yosefhflix_mylist", JSON.stringify(myList));
  }, [myList]);

  const toggleMyList = (movie) => {
    if (myList.some(m => m.id === movie.id)) {
        setMyList(myList.filter(m => m.id !== movie.id));
    } else {
        setMyList([...myList, movie]);
    }
  };

  useEffect(() => {
    const handleScroll = () => setIsScrolled(window.scrollY > 0);
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  useEffect(() => {
    axios.get('http://localhost:8081/api/peliculas')
      .then(response => {
        const shuffled = response.data.sort(() => 0.5 - Math.random());
        setMovies(shuffled);
      })
      .catch(error => console.error("Error cargando películas:", error));
  }, [])

  const hasKeywords = (movie, keywords) => {
      const text = (movie.sinopsis + " " + movie.titulo).toLowerCase();
      return keywords.some(k => text.includes(k));
  }

  const changeView = (newView) => {
      setView(newView);
      window.scrollTo({ top: 0, behavior: 'smooth' });
      setIsSearchOpen(false);
      setSearchTerm("");
  };

  const heroMovies = movies.slice(0, 5);
  const trendingMovies = movies.slice(5, 25);
  const topRatedMovies = movies.filter(m => m.valoracion >= 7.5);
  const actionMovies = movies.filter(m => hasKeywords(m, ["acción", "guerra", "batalla", "asesino", "misión", "héroe", "policía"]) || m.duracion > 140);
  const comedyMovies = movies.filter(m => hasKeywords(m, ["comedia", "risa", "animación", "familia", "amigos", "viaje", "divertido"]));
  const horrorMovies = movies.filter(m => hasKeywords(m, ["miedo", "terror", "muerte", "oscuro", "fantasma", "asesinato", "sangre", "demonio", "suspenso", "psicópata"]));
  const scifiMovies = movies.filter(m => hasKeywords(m, ["espacio", "futuro", "alien", "robot", "ciencia", "tecnología", "universo", "planeta", "estrella"]));
  
  const searchResults = searchTerm.length > 0 
    ? movies.filter(m => m.titulo.toLowerCase().includes(searchTerm.toLowerCase()))
    : [];

  const handleMovieClick = (movie) => {
      setSelectedMovie(movie);
      document.body.style.overflow = 'hidden';
  }
  const handleCloseModal = () => {
      setSelectedMovie(null);
      document.body.style.overflow = 'unset';
  }

  // --- REJILLA CON TOQUES DORADOS ---
  const MovieGrid = ({ title, movieList, emptyMessage }) => (
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

  return (
    <div className="min-h-screen bg-[#141414] text-white font-sans overflow-x-hidden selection:bg-[#E5A909] selection:text-black flex flex-col justify-between">
      
      {selectedMovie && (
          <MovieModal 
            movie={selectedMovie} 
            onClose={handleCloseModal} 
            onToggleMyList={toggleMyList}
            isFavorite={myList.some(m => m.id === selectedMovie.id)}
          />
      )}

      {/* NAVBAR */}
      <nav className={`fixed top-0 w-full p-4 px-4 md:px-10 z-40 flex items-center justify-between transition-all duration-500 ${isScrolled ? 'bg-[#141414] shadow-lg border-b border-gray-800' : 'bg-gradient-to-b from-black/90 to-transparent'}`}>
          <div className="flex items-center gap-10">
             {/* LOGO DORADO */}
             <h1 
                className="text-3xl md:text-4xl font-extrabold text-[#E5A909] cursor-pointer tracking-tighter drop-shadow-md"
                style={{ textShadow: "0px 0px 10px rgba(229, 169, 9, 0.3)" }}
                onClick={() => changeView('home')} 
             >
                Joseph_Link
             </h1>
             
             {!isSearchOpen && (
                 <ul className="hidden md:flex gap-6 text-sm text-gray-300 font-medium items-center">
                    <li 
                        onClick={() => changeView('home')}
                        className={`cursor-pointer transition hover:text-[#E5A909] flex items-center gap-2 ${view === 'home' ? 'font-bold text-white' : ''}`}
                    >
                        <Home size={18} /> Inicio
                    </li>
                    <li 
                        onClick={() => changeView('series')}
                        className={`cursor-pointer transition hover:text-[#E5A909] flex items-center gap-2 ${view === 'series' ? 'font-bold text-white' : ''}`}
                    >
                        <Tv size={18} /> Series
                    </li>
                    <li 
                        onClick={() => changeView('movies')}
                        className={`cursor-pointer transition hover:text-[#E5A909] flex items-center gap-2 ${view === 'movies' ? 'font-bold text-white' : ''}`}
                    >
                        <Film size={18} /> Películas
                    </li>
                    <li 
                        onClick={() => changeView('mylist')}
                        className={`cursor-pointer transition hover:text-[#E5A909] flex items-center gap-2 ${view === 'mylist' ? 'font-bold text-white' : ''}`}
                    >
                        <Bookmark size={18} /> Mi Lista
                    </li>
                 </ul>
             )}
          </div>

          <div className="flex items-center gap-6 text-gray-300">
             
             {/* 1. BUSCADOR */}
             <div className={`flex items-center border border-white/0 ${isSearchOpen ? 'bg-black/80 border-[#E5A909]/50 px-2 py-1' : ''} transition-all duration-300 rounded`}>
                <div className="flex items-center gap-2 cursor-pointer hover:text-[#E5A909]" onClick={() => setIsSearchOpen(!isSearchOpen)}>
                    <Search className="w-6 h-6" />
                    {!isSearchOpen && <span className="text-sm font-medium hidden lg:block">Buscar</span>}
                </div>
                
                <input 
                    type="text"
                    placeholder="Títulos, géneros..."
                    className={`bg-transparent text-white text-sm border-none focus:ring-0 outline-none transition-all duration-300 ${isSearchOpen ? 'w-48 md:w-64 ml-2 opacity-100' : 'w-0 opacity-0'}`}
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
                {searchTerm.length > 0 && (
                    <X size={18} className="cursor-pointer hover:text-[#E5A909] ml-2" onClick={() => setSearchTerm("")} />
                )}
             </div>

             {/* 2. CAMPANA (Ahora clickeable) */}
             <div 
                onClick={() => changeView('news')}
                className={`flex items-center gap-2 cursor-pointer hover:text-[#E5A909] transition group ${view === 'news' ? 'text-[#E5A909] font-bold' : ''}`}
             >
                <Bell className="w-6 h-6" />
                <span className="text-sm font-medium hidden lg:block group-hover:text-[#E5A909]">Novedades</span>
             </div>

             {/* 3. AVATAR (Ahora clickeable) */}
             <div 
                onClick={() => changeView('profile')}
                className="flex items-center gap-2 cursor-pointer group"
             >
                <div className={`w-8 h-8 rounded flex items-center justify-center font-bold text-black shadow-lg shadow-yellow-900/20 group-hover:scale-105 transition ${view === 'profile' ? 'bg-white ring-2 ring-[#E5A909]' : 'bg-[#B8860B]'}`}>
                    J
                </div>
                <span className={`text-sm font-medium hidden lg:block group-hover:text-[#E5A909] transition ${view === 'profile' ? 'text-[#E5A909]' : ''}`}>Perfil</span>
             </div>

          </div>
      </nav>

      {/* --- CONTENIDO PRINCIPAL --- */}
      <div className="flex-grow">
        {/* 1. BÚSQUEDA */}
        {searchTerm.length > 0 ? (
            <MovieGrid 
                title={`Resultados: "${searchTerm}"`} 
                movieList={searchResults} 
                emptyMessage="No encontramos nada con ese nombre."
            />

        /* 2. MI LISTA */
        ) : view === 'mylist' ? (
            <MovieGrid 
                title="Mi Lista Personal" 
                movieList={myList} 
                emptyMessage="Aún no has añadido ninguna película a tu lista."
            />

        /* 3. PELÍCULAS */
        ) : view === 'movies' ? (
            <MovieGrid 
                title="Catálogo Completo" 
                movieList={movies} 
                emptyMessage="No hay películas disponibles."
            />

        /* 4. SERIES (Próximamente) */
        ) : view === 'series' ? (
            <div className="flex flex-col items-center justify-center min-h-[70vh] text-gray-500 animate-fade-in bg-gradient-to-b from-[#141414] to-black">
                <div className="p-8 bg-[#1f1f1f] rounded-full mb-8 shadow-2xl shadow-[#E5A909]/20 border border-gray-800">
                    <Tv size={80} className="text-[#E5A909]" />
                </div>
                <h2 className="text-4xl md:text-6xl font-extrabold text-white mb-6 tracking-tight text-center">
                    Próximamente...
                </h2>
                <p className="text-xl md:text-2xl text-gray-400 max-w-2xl text-center leading-relaxed">
                    Estamos trabajando para traerte las mejores series del momento. <br/>
                    ¡Vuelve muy pronto!
                </p>
                <button 
                    onClick={() => changeView('home')} 
                    className="mt-12 bg-[#E5A909] text-black px-10 py-4 rounded font-bold hover:bg-yellow-500 transition shadow-lg hover:scale-105"
                >
                    Volver al Inicio
                </button>
            </div>

        /* 5. NOVEDADES (Igual que series) */
        ) : view === 'news' ? (
            <div className="flex flex-col items-center justify-center min-h-[70vh] text-gray-500 animate-fade-in bg-gradient-to-b from-[#141414] to-black">
                <div className="p-8 bg-[#1f1f1f] rounded-full mb-8 shadow-2xl shadow-[#E5A909]/20 border border-gray-800">
                    <Bell size={80} className="text-[#E5A909]" />
                </div>
                <h2 className="text-4xl md:text-6xl font-extrabold text-white mb-6 tracking-tight text-center">
                    Próximamente...
                </h2>
                <p className="text-xl md:text-2xl text-gray-400 max-w-2xl text-center leading-relaxed">
                    Te avisaremos de los estrenos más esperados. <br/>
                    La sección de novedades está en construcción.
                </p>
                <button 
                    onClick={() => changeView('home')} 
                    className="mt-12 bg-[#E5A909] text-black px-10 py-4 rounded font-bold hover:bg-yellow-500 transition shadow-lg hover:scale-105"
                >
                    Volver al Inicio
                </button>
            </div>

        /* 6. PERFIL (Mensaje específico) */
        ) : view === 'profile' ? (
            <div className="flex flex-col items-center justify-center min-h-[70vh] text-gray-500 animate-fade-in bg-gradient-to-b from-[#141414] to-black">
                <div className="p-8 bg-[#1f1f1f] rounded-full mb-8 shadow-2xl shadow-[#E5A909]/20 border border-gray-800">
                    <User size={80} className="text-[#E5A909]" />
                </div>
                <h2 className="text-4xl md:text-6xl font-extrabold text-white mb-6 tracking-tight text-center px-4">
                    Próximamente se habilitará la página al público
                </h2>
                <p className="text-xl md:text-2xl text-gray-400 max-w-2xl text-center leading-relaxed mt-4">
                    Estamos ultimando los detalles de tu espacio personal.
                </p>
                <button 
                    onClick={() => changeView('home')} 
                    className="mt-12 bg-[#E5A909] text-black px-10 py-4 rounded font-bold hover:bg-yellow-500 transition shadow-lg hover:scale-105"
                >
                    Volver al Inicio
                </button>
            </div>

        /* 7. INICIO (HOME) - DEFAULT */
        ) : (
            <>
                <div className="relative h-[85vh] w-full">
                    {heroMovies.length > 0 && (
                    <Swiper
                        modules={[Autoplay, EffectFade]}
                        effect={'fade'}
                        speed={1500}
                        autoplay={{ delay: 6000, disableOnInteraction: false }}
                        loop={true}
                        allowTouchMove={false}
                        className="h-full w-full"
                    >
                        {heroMovies.map((movie) => (
                        <SwiperSlide key={movie.id}>
                            <div className="relative h-full w-full bg-cover bg-center" style={{ backgroundImage: `url(https://image.tmdb.org/t/p/original${movie.backdropPath})` }}>
                            <div className="absolute inset-0 bg-gradient-to-t from-[#141414] via-[#141414]/20 to-black/40"></div>
                            <div className="absolute inset-0 bg-gradient-to-r from-[#141414]/90 via-transparent to-transparent"></div>
                            <div className="absolute bottom-32 left-4 md:left-16 max-w-2xl p-4 animate-fade-in-up">
                                <h2 className="text-5xl md:text-7xl font-extrabold drop-shadow-2xl mb-4 leading-none text-white">{movie.titulo}</h2>
                                <p className="text-lg md:text-xl text-gray-200 drop-shadow-md mb-8 line-clamp-3 font-medium">{movie.sinopsis}</p>
                                <div className="flex gap-4">
                                <button onClick={() => handleMovieClick(movie)} className="flex items-center gap-3 bg-white text-black px-8 py-3 rounded hover:bg-[#E5A909] hover:text-black transition font-bold text-lg">
                                    <Play fill="black" size={24} /> Reproducir
                                </button>
                                <button onClick={() => handleMovieClick(movie)} className="flex items-center gap-3 bg-gray-500/40 text-white px-8 py-3 rounded hover:bg-gray-500/30 transition font-bold text-lg backdrop-blur-md">
                                    <Info size={24} /> Más información
                                </button>
                                </div>
                            </div>
                            </div>
                        </SwiperSlide>
                        ))}
                    </Swiper>
                    )}
                </div>

                <div className="relative z-10 -mt-24 pb-20 space-y-2 pl-4 md:pl-12">
                    {myList.length > 0 && (
                        <MovieRow title="Mi Lista" movies={myList} onMovieClick={handleMovieClick} />
                    )}
                    <MovieRow title="Tendencias Ahora" movies={trendingMovies} onMovieClick={handleMovieClick} />
                    {topRatedMovies.length > 0 && <MovieRow title="Aclamadas por la crítica" movies={topRatedMovies} onMovieClick={handleMovieClick} />}
                    {actionMovies.length > 0 && <MovieRow title="Acción y Adrenalina" movies={actionMovies} onMovieClick={handleMovieClick} />}
                    {comedyMovies.length > 0 && <MovieRow title="Risas y Familia" movies={comedyMovies} onMovieClick={handleMovieClick} />}
                    {horrorMovies.length > 0 && <MovieRow title="Terror y Suspense" movies={horrorMovies} onMovieClick={handleMovieClick} />}
                    {scifiMovies.length > 0 && <MovieRow title="Mundos Fantásticos" movies={scifiMovies} onMovieClick={handleMovieClick} />}
                </div>
            </>
        )}
      </div>

      {/* --- FOOTER DORADO-OSCURO --- */}
      <footer className="bg-[#0f0f0f] text-gray-400 py-12 px-4 md:px-12 border-t border-gray-800 animate-fade-in relative z-10">
          <div className="max-w-6xl mx-auto grid grid-cols-2 md:grid-cols-4 gap-8">
              <div>
                  <h3 className="text-white font-bold mb-4">Navegación</h3>
                  <ul className="space-y-2 text-sm">
                      <li className="hover:text-[#E5A909] cursor-pointer transition" onClick={() => changeView('home')}>Inicio</li>
                      <li className="hover:text-[#E5A909] cursor-pointer transition" onClick={() => changeView('movies')}>Películas</li>
                      <li className="hover:text-[#E5A909] cursor-pointer transition" onClick={() => changeView('series')}>Series</li>
                      <li className="hover:text-[#E5A909] cursor-pointer transition" onClick={() => changeView('mylist')}>Mi Lista</li>
                  </ul>
              </div>
              <div>
                  <h3 className="text-white font-bold mb-4">Legal</h3>
                  <ul className="space-y-2 text-sm">
                      <li className="hover:text-[#E5A909] cursor-pointer transition">Aviso Legal</li>
                      <li className="hover:text-[#E5A909] cursor-pointer transition">Privacidad</li>
                      <li className="hover:text-[#E5A909] cursor-pointer transition">Cookies</li>
                  </ul>
              </div>
              <div>
                  <h3 className="text-white font-bold mb-4">Ayuda</h3>
                  <ul className="space-y-2 text-sm">
                      <li className="hover:text-[#E5A909] cursor-pointer transition">Preguntas Frecuentes</li>
                      <li className="hover:text-[#E5A909] cursor-pointer transition">Contacto</li>
                      <li className="hover:text-[#E5A909] cursor-pointer transition">Soporte</li>
                  </ul>
              </div>
              <div>
                  <h3 className="text-[#E5A909] font-bold mb-4 text-lg">Joseph_Link</h3>
                  <p className="text-sm mb-4">Tu plataforma de streaming favorita. Versión Gold Edition.</p>
                  <div className="flex gap-4 mt-4">
                      <a href="#" className="text-gray-400 hover:text-[#E5A909] transition"><Facebook size={20} /></a>
                      <a href="#" className="text-gray-400 hover:text-[#E5A909] transition"><Twitter size={20} /></a>
                      <a href="#" className="text-gray-400 hover:text-[#E5A909] transition"><Instagram size={20} /></a>
                  </div>
              </div>
          </div>
          <div className="mt-12 pt-8 border-t border-gray-800 text-center text-sm">
              <p>© {new Date().getFullYear()} Joseph_Link. Todos los derechos reservados.</p>
          </div>
      </footer>

    </div>
  )
}

export default App