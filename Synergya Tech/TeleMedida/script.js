 import { createClient } from "https://esm.sh/@supabase/supabase-js@2";
 
 const SUPABASE_URL = "TU_SUPABASE_URL";
 const SUPABASE_KEY = "TU_SUPABASE_ANON_KEY";
 
    const supabase = createClient(SUPABASE_URL, SUPABASE_KEY);
    
    const form = document.getElementById("formulario");
    
    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const punto = parseInt(document.getElementById("punto").value);
        const instantaneo = parseFloat(document.getElementById("instantaneo").value);
        const totalizado = parseFloat(document.getElementById("totalizado").value);
        const fotoFile = document.getElementById("foto").files[0];
        
        if (!fotoFile) {
            alert("Selecciona una foto");
            return;
        }

        try {
            const fileName = `${Date.now()}_${fotoFile.name}`;
            const { data: fotoData, error: fotoError } = await supabase
            .storage
            .from("fotos-totalizador")
            .upload(fileName, fotoFile);
            
        if (fotoError) throw fotoError;
        
        const fotoUrl = `${SUPABASE_URL}/storage/v1/object/public/fotos-totalizador/${fileName}`;
        
        const { error } = await supabase
        .from("mediciones")
        .insert([
            {
                punto_control: punto,
                caudal_instantaneo: instantaneo,
                caudal_totalizado: totalizado,
                foto_url: fotoUrl
            }
          ]);

          if (error) throw error;

          alert("✅ Datos guardados correctamente");
          form.reset();
        } catch (err) {
            console.error(err);
            alert("❌ Error: " + (err.message || JSON.stringify(err)));
        }
    });