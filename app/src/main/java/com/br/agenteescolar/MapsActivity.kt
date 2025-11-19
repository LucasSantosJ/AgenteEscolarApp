package com.br.agenteescolar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

class MapActivity : AppCompatActivity() {

    private var mapView: MapView? = null // Variável para guardar nosso mapa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- Configuração IMPORTANTE do osmdroid ---
        // Isso "diz" ao osmdroid onde salvar os pedaços do mapa (cache)
        // e como se identificar (User-Agent)
        val ctx = applicationContext
        Configuration.getInstance().load(ctx, androidx.preference.PreferenceManager.getDefaultSharedPreferences(ctx))
        Configuration.getInstance().userAgentValue = packageName
        // --- Fim da Configuração ---

        // Carrega o nosso "desenho" (layout)
        setContentView(R.layout.activity_map)

        // "Linka" a variável do Kotlin com o componente do XML
        mapView = findViewById(R.id.map_osm)

        // Configura o tipo de mapa (padrão)
        mapView?.setTileSource(TileSourceFactory.MAPNIK)

        // Ativa os controles de zoom (gesto de pinça)
        mapView?.setMultiTouchControls(true)

        // --- Centralizando o Mapa em um Ponto Inicial ---
        // Vamos centralizar no mapa do Brasil por enquanto
        val mapController = mapView?.controller
        mapController?.setZoom(5.0) // Nível de zoom (quanto menor, mais longe)
        val startPoint = GeoPoint(-15.7801, -47.9292) // Coordenadas de Brasília
        mapController?.setCenter(startPoint)
    }

    // --- Funções de "Ciclo de Vida" para o mapa funcionar bem ---
    // (Pausar o mapa quando o app sai da tela, etc)

    override fun onResume() {
        super.onResume() // Esta linha é importante
        mapView?.onResume() // Necessário para o mapa
    }

    override fun onPause() {
        super.onPause() // Esta linha é importante
        mapView?.onPause() // Necessário para o mapa
    }


}