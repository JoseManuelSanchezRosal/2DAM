import { useState, useEffect } from 'react'
import axios from 'axios'
// AÑADIDO: Importamos los iconos Sun, Cloud, Moon
import { Play, Info, Search, Bell, X, Tv, Home, Film, Bookmark, User, Linkedin, Github, Mail, Phone, Sun, Cloud, Moon } from 'lucide-react'
import { Swiper, SwiperSlide } from 'swiper/react';
import { Autoplay, EffectFade } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/effect-fade';

import MovieRow from './components/MovieRow'
import MovieModal from './components/MovieModal'
import MovieGrid from './components/MovieGrid'

const IMAGE_URL = "https://image.tmdb.org/t/p/w500"; 
const GOLD_COLOR = "#E5A909"; 

// --- CONFIGURACIÓN DE TEMAS ---
const THEMES = {
  day: {
    id: 'day',
    bg: 'bg-[#f3f4f6]', // Gris muy claro (casi blanco)
    text: 'text-gray-900', // Texto oscuro
    navbarBg: 'bg-white',
    footerBg: 'bg-gray-200',
    heroHex: '#f3f4f6', // <--- AÑADIDO: Color exacto para el degradado (Día)
    icon: <Sun size={20} className="text-orange-500 fill-orange-500" />,
    label: 'Día'
  },
  afternoon: {
    id: 'afternoon',
    bg: 'bg-[#475569]', // Slate-600 (Azul grisáceo medio)
    text: 'text-white', // Texto blanco
    navbarBg: 'bg-[#334155]',
    footerBg: 'bg-[#1e293b]',
    heroHex: '#475569', // <--- AÑADIDO: Color exacto para el degradado (Tarde)
    icon: <Cloud size={20} className="text-blue-300 fill-blue-300" />,
    label: 'Tarde'
  },
  night: {
    id: 'night',
    bg: 'bg-[#141414]', // Tu negro original
    text: 'text-white', // Texto blanco
    navbarBg: 'bg-[#141414]',
    footerBg: 'bg-[#0f0f0f]',
    heroHex: '#141414', // <--- AÑADIDO: Color exacto para el degradado (Noche)
    icon: <Moon size={20} className="text-[#E5A909] fill-[#E5A909]" />,
    label: 'Noche'
  }
};

const LEGAL_CONTENT = {
  aviso: {
    titulo: "Aviso Legal",
    texto: "Bienvenido a Joseph_Link. Esta plataforma es un proyecto de demostración propiedad de Joseph_Link S.L. (Sociedad Limitada de Streaming). Todos los contenidos mostrados aquí provienen de APIs públicas (TMDB) y se utilizan únicamente con fines educativos y de desarrollo. No alojamos contenido ilegal en nuestros servidores. Domicilio social: Calle del Código, 123, 28000, Servidor Central."
  },
  privacidad: {
    titulo: "Política de Privacidad",
    texto: "En Joseph_Link nos tomamos tu privacidad muy en serio (casi tanto como nuestras series). Recopilamos datos básicos de navegación para mejorar tu experiencia de usuario, pero prometemos no venderlos a villanos de películas ni a imperios galácticos malvados. Tus datos están seguros y encriptados bajo protocolos de seguridad de nivel 'Agente Secreto'."
  },
  cookies: {
    titulo: "Política de Cookies",
    texto: "Utilizamos cookies propias y de terceros. No son de chocolate (lamentablemente), sino pequeños archivos de texto que nos ayudan a recordar si dejaste una película a medias o si prefieres el modo oscuro. Al navegar por Joseph_Link, aceptas que guardemos estas 'migajas' digitales en tu navegador para que la web funcione fluida como una escena de acción bien coreografiada."
  }
};

function App() {
  const [movies, setMovies] = useState([])
  const [isScrolled, setIsScrolled] = useState(false);
  const [selectedMovie, setSelectedMovie] = useState(null);
  
  const [isSearchOpen, setIsSearchOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [view, setView] = useState('home'); 
  
  // AÑADIDO: Estado para el tema (por defecto noche)
  const [currentTheme, setCurrentTheme] = useState('night');

  const [myList, setMyList] = useState(() => {
    const saved = localStorage.getItem("yosefhflix_mylist");
    return saved ? JSON.parse(saved) : [];
  });

  useEffect(() => {
    localStorage.setItem("yosefhflix_mylist", JSON.stringify(myList));
  }, [myList]);

  // AÑADIDO: Función para rotar tema (Día -> Tarde -> Noche)
  const cycleTheme = () => {
    if (currentTheme === 'day') setCurrentTheme('afternoon');
    else if (currentTheme === 'afternoon') setCurrentTheme('night');
    else setCurrentTheme('day');
  };

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

  return (
    // AÑADIDO: Clase dinámica de tema en el div principal
    <div className={`min-h-screen font-sans overflow-x-hidden selection:bg-[#E5A909] selection:text-black flex flex-col justify-between transition-colors duration-500 ease-in-out ${THEMES[currentTheme].bg} ${THEMES[currentTheme].text} theme-${currentTheme}`}>
      
      {/* AÑADIDO: ESTILOS FORZADOS PARA MODO DÍA */}
      {/* Esto arregla que no se vean las letras en blanco sobre blanco */}
      <style>{`
        /* Si el tema es día, forzamos los textos blancos a ser oscuros */
        .theme-day .text-white { color: #1f2937 !important; }
        .theme-day .text-gray-300 { color: #4b5563 !important; }
        .theme-day .text-gray-400 { color: #6b7280 !important; }
        /* Excepción: Textos sobre imágenes o botones que SIEMPRE deben ser blancos */
        .theme-day .bg-black .text-white, 
        .theme-day button.text-white,
        .theme-day .gold-glow h3 { color: #ffffff !important; }
      `}</style>

      {selectedMovie && (
          <MovieModal 
            movie={selectedMovie} 
            onClose={handleCloseModal} 
            onToggleMyList={toggleMyList}
            isFavorite={myList.some(m => m.id === selectedMovie.id)}
          />
      )}

      {/* NAVBAR */}
      {/* AÑADIDO: El background del navbar ahora cambia según el tema si haces scroll */}
      <nav className={`fixed top-0 w-full p-4 px-4 md:px-10 z-40 flex items-center justify-between transition-all duration-500 ${isScrolled ? `${THEMES[currentTheme].navbarBg} shadow-lg border-b border-gray-700/50` : 'bg-gradient-to-b from-black/90 to-transparent'}`}>
          <div className="flex items-center gap-10">
             <h1 
                className="text-3xl md:text-4xl font-extrabold text-[#E5A909] cursor-pointer tracking-tighter drop-shadow-md"
                style={{ textShadow: "0px 0px 10px rgba(229, 169, 9, 0.3)" }}
                onClick={() => changeView('home')} 
             >
                Joseph_Link
             </h1>
             
             {!isSearchOpen && (
                 <ul className={`hidden md:flex gap-6 text-sm font-medium items-center ${currentTheme === 'day' && isScrolled ? 'text-gray-800' : 'text-gray-300'}`}>
                    <li 
                        onClick={() => changeView('home')}
                        className={`cursor-pointer transition hover:text-[#E5A909] flex items-center gap-2 ${view === 'home' ? 'font-bold text-[#E5A909]' : ''}`}
                    >
                        <Home size={18} /> Inicio
                    </li>
                    <li 
                        onClick={() => changeView('series')}
                        className={`cursor-pointer transition hover:text-[#E5A909] flex items-center gap-2 ${view === 'series' ? 'font-bold text-[#E5A909]' : ''}`}
                    >
                        <Tv size={18} /> Series
                    </li>
                    <li 
                        onClick={() => changeView('movies')}
                        className={`cursor-pointer transition hover:text-[#E5A909] flex items-center gap-2 ${view === 'movies' ? 'font-bold text-[#E5A909]' : ''}`}
                    >
                        <Film size={18} /> Películas
                    </li>
                    <li 
                        onClick={() => changeView('mylist')}
                        className={`cursor-pointer transition hover:text-[#E5A909] flex items-center gap-2 ${view === 'mylist' ? 'font-bold text-[#E5A909]' : ''}`}
                    >
                        <Bookmark size={18} /> Mi Lista
                    </li>
                 </ul>
             )}
          </div>

          <div className={`flex items-center gap-6 ${currentTheme === 'day' && isScrolled ? 'text-gray-800' : 'text-gray-300'}`}>
             
             {/* AÑADIDO: EL BOTÓN DE CAMBIO DE TEMA */}
             <button 
                onClick={cycleTheme}
                className="cursor-pointer hover:scale-110 transition-transform p-2 rounded-full hover:bg-white/10 flex items-center justify-center border border-transparent hover:border-[#E5A909]/50"
                title={`Cambiar a modo: ${currentTheme === 'day' ? 'Tarde' : currentTheme === 'afternoon' ? 'Noche' : 'Día'}`}
             >
                {THEMES[currentTheme].icon}
             </button>

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

             {/* 2. CAMPANA */}
             <div 
                onClick={() => changeView('news')}
                className={`flex items-center gap-2 cursor-pointer hover:text-[#E5A909] transition group ${view === 'news' ? 'text-[#E5A909] font-bold' : ''}`}
             >
                <Bell className="w-6 h-6" />
                <span className="text-sm font-medium hidden lg:block group-hover:text-[#E5A909]">Novedades</span>
             </div>

             {/* 3. AVATAR */}
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
                handleMovieClick={handleMovieClick}
                changeView={changeView}
            />

        /* 2. MI LISTA */
        ) : view === 'mylist' ? (
            <MovieGrid 
                title="Mi Lista Personal" 
                movieList={myList} 
                emptyMessage="Aún no has añadido ninguna película a tu lista."
                handleMovieClick={handleMovieClick}
                changeView={changeView}
            />

        /* 3. PELÍCULAS */
        ) : view === 'movies' ? (
            <MovieGrid 
                title="Catálogo Completo" 
                movieList={movies} 
                emptyMessage="No hay películas disponibles."
                handleMovieClick={handleMovieClick}
                changeView={changeView}
            />

        /* 4. SERIES */
        ) : view === 'series' ? (
            <div className="flex flex-col items-center justify-center min-h-[70vh] text-gray-500 animate-fade-in pt-32">
                <div className="p-8 bg-[#1f1f1f] rounded-full mb-8 shadow-2xl shadow-[#E5A909]/20 border border-gray-800">
                    <Tv size={80} className="text-[#E5A909]" />
                </div>
                <h2 className="text-4xl md:text-6xl font-extrabold mb-6 tracking-tight text-center">
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

        /* 5. NOVEDADES */
        ) : view === 'news' ? (
            <div className="flex flex-col items-center justify-center min-h-[70vh] text-gray-500 animate-fade-in pt-32">
                <div className="p-8 bg-[#1f1f1f] rounded-full mb-8 shadow-2xl shadow-[#E5A909]/20 border border-gray-800">
                    <Bell size={80} className="text-[#E5A909]" />
                </div>
                <h2 className="text-4xl md:text-6xl font-extrabold mb-6 tracking-tight text-center">
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

        /* 6. PERFIL */
        ) : view === 'profile' ? (
            <div className="flex flex-col items-center justify-center min-h-[70vh] text-gray-500 animate-fade-in pt-32">
                <div className="p-8 bg-[#1f1f1f] rounded-full mb-8 shadow-2xl shadow-[#E5A909]/20 border border-gray-800">
                    <User size={80} className="text-[#E5A909]" />
                </div>
                <h2 className="text-4xl md:text-6xl font-extrabold mb-6 tracking-tight text-center px-4">
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

        /* 7. AYUDA (NUEVO) */
        ) : view === 'help' ? (
            <div className="flex flex-col items-center justify-center min-h-[70vh] text-gray-500 animate-fade-in pt-32">
                <div className="p-8 bg-[#1f1f1f] rounded-full mb-8 shadow-2xl shadow-[#E5A909]/20 border border-gray-800">
                    <Info size={80} className="text-[#E5A909]" />
                </div>
                <h2 className="text-4xl md:text-6xl font-extrabold mb-6 tracking-tight text-center px-4">
                    Centro de Ayuda
                </h2>
                <p className="text-xl md:text-2xl text-gray-400 max-w-2xl text-center leading-relaxed mt-4">
                    Nuestro equipo de soporte está configurando las líneas. <br/>
                    Esta sección se abrirá próximamente al público.
                </p>
                <button 
                    onClick={() => changeView('home')} 
                    className="mt-12 bg-[#E5A909] text-black px-10 py-4 rounded font-bold hover:bg-yellow-500 transition shadow-lg hover:scale-105"
                >
                    Volver al Inicio
                </button>
            </div>

        /* 8. LEGAL (NUEVO - MANEJA AVISO, PRIVACIDAD, COOKIES) */
        ) : view.startsWith('legal-') ? (
            <div className="pt-32 px-4 md:px-12 min-h-[70vh] animate-fade-in max-w-4xl mx-auto">
                <div className="bg-[#1f1f1f] p-8 md:p-12 rounded-lg border border-gray-800 shadow-2xl">
                    <h2 className="text-3xl font-bold text-[#E5A909] mb-6">
                        {view === 'legal-aviso' ? LEGAL_CONTENT.aviso.titulo : 
                         view === 'legal-privacidad' ? LEGAL_CONTENT.privacidad.titulo : 
                         LEGAL_CONTENT.cookies.titulo}
                    </h2>
                    <div className="prose prose-invert prose-lg text-gray-300 leading-relaxed">
                        <p>
                            {view === 'legal-aviso' ? LEGAL_CONTENT.aviso.texto : 
                             view === 'legal-privacidad' ? LEGAL_CONTENT.privacidad.texto : 
                             LEGAL_CONTENT.cookies.texto}
                        </p>
                        <p className="mt-6 text-sm text-gray-500 italic">
                            * Última actualización: {new Date().toLocaleDateString()}
                        </p>
                    </div>
                    <button onClick={() => changeView('home')} className="mt-8 text-white hover:text-[#E5A909] underline underline-offset-4 decoration-[#E5A909]">
                        ← Volver a navegar
                    </button>
                </div>
            </div>

        /* 9. INICIO (HOME) - DEFAULT */
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
                            
                            {/* --- AQUÍ ESTÁ EL CAMBIO --- */}
                            {/* He quitado 'bg-gradient-to-t from-current...' que usaba el color del texto */}
                            {/* Ahora uso 'style={{ background: ... }}' con el color HEX exacto de cada tema */}
                            <div 
                                className="absolute inset-0"
                                style={{ background: `linear-gradient(to top, ${THEMES[currentTheme].heroHex} 1%, transparent 15%, rgba(0,0,0,0.4) 100%)` }}
                            ></div>
                            
                            {/* Gradiente Lateral (siempre oscuro para que se lea el texto blanco) */}
                            <div className="absolute inset-0 bg-gradient-to-r from-black/80 via-transparent to-transparent"></div>
                            
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

      {/* --- FOOTER (CON FONDO ADAPTATIVO) --- */}
      <footer className={`py-12 px-4 md:px-12 border-t border-gray-800 animate-fade-in relative z-10 ${THEMES[currentTheme].footerBg} transition-colors duration-500`}>
          <div className="max-w-6xl mx-auto grid grid-cols-2 md:grid-cols-4 gap-8">
              <div>
                  <h3 className={`font-bold mb-4 ${currentTheme === 'day' ? 'text-gray-800' : 'text-white'}`}>Navegación</h3>
                  <ul className="space-y-2 text-sm">
                      <li className="hover:text-[#E5A909] cursor-pointer transition" onClick={() => changeView('home')}>Inicio</li>
                      <li className="hover:text-[#E5A909] cursor-pointer transition" onClick={() => changeView('movies')}>Películas</li>
                      <li className="hover:text-[#E5A909] cursor-pointer transition" onClick={() => changeView('series')}>Series</li>
                      <li className="hover:text-[#E5A909] cursor-pointer transition" onClick={() => changeView('mylist')}>Mi Lista</li>
                  </ul>
              </div>
              <div>
                  <h3 className={`font-bold mb-4 ${currentTheme === 'day' ? 'text-gray-800' : 'text-white'}`}>Legal</h3>
                  <ul className="space-y-2 text-sm">
                      <li className="hover:text-[#E5A909] cursor-pointer transition" onClick={() => changeView('legal-aviso')}>Aviso Legal</li>
                      <li className="hover:text-[#E5A909] cursor-pointer transition" onClick={() => changeView('legal-privacidad')}>Privacidad</li>
                      <li className="hover:text-[#E5A909] cursor-pointer transition" onClick={() => changeView('legal-cookies')}>Cookies</li>
                  </ul>
              </div>
              <div>
                  <h3 className={`font-bold mb-4 ${currentTheme === 'day' ? 'text-gray-800' : 'text-white'}`}>Ayuda</h3>
                  <ul className="space-y-2 text-sm">
                      <li className="hover:text-[#E5A909] cursor-pointer transition" onClick={() => changeView('help')}>Preguntas Frecuentes</li>
                      <li className="hover:text-[#E5A909] cursor-pointer transition" onClick={() => changeView('help')}>Contacto</li>
                      <li className="hover:text-[#E5A909] cursor-pointer transition" onClick={() => changeView('help')}>Soporte</li>
                  </ul>
              </div>
              <div>
                  <h3 className="text-[#E5A909] font-bold mb-4 text-lg">Joseph_Link</h3>
                  <p className="text-sm mb-4">Tu plataforma de streaming favorita. Versión Gold Edition.</p>
                  
                  {/* ICONOS REDES */}
                  <div className="flex gap-4 mt-4">
                      {/* LINKEDIN */}
                      <a href="https://www.linkedin.com/in/josé-manuel-sánchez-rosal-863803114" target="_blank" rel="noopener noreferrer" className="hover:text-[#E5A909] transition" title="LinkedIn">
                          <Linkedin size={20} />
                      </a>
                      {/* GITHUB */}
                      <a href="https://github.com/JoseManuelSanchezRosal" target="_blank" rel="noopener noreferrer" className="hover:text-[#E5A909] transition" title="GitHub">
                          <Github size={20} />
                      </a>
                      {/* EMAIL */}
                      <a href="mailto:j.manuel25@outlook.es" className="hover:text-[#E5A909] transition" title="Enviar Correo">
                          <Mail size={20} />
                      </a>
                      {/* TELEFONO */}
                      <a href="tel:649745624" className="hover:text-[#E5A909] transition" title="Llamar">
                          <Phone size={20} />
                      </a>
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