// Creamos un ámbito privado.
const gestorContador = (function() {
  let contador = 0; // Variable privada, no se puede acceder desde fuera.

  function incrementar() {
    contador++;
    document.getElementById("cont").innerHTML = contador + "";
  }

  function restablecer() {
    contador = 0;
    document.getElementById("cont").innerHTML = contador + "";
  }

  // Devolvemos un objeto mediante los métodos públicos.
  return {
    contadorLocal: function(indice) { // Para devolver una función publica, utilizamos nomb_func: function()
      if (indice === 1) {
        incrementar();
      } else {
        restablecer();
      }
    }
  };

})();