package com.br.agenteescolar.screens

import android.content.Context
import android.location.Geocoder
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.br.agenteescolar.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.util.Locale

@Composable
fun MapaScreen() {
    val context = LocalContext.current

    val mapView = remember {
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setZoom(10.0)
            controller.setCenter(GeoPoint(-9.66625, -35.7351)) // Ponto inicial (Maceió)
        }
    }

    LaunchedEffect(Unit) {
        Configuration.getInstance().load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
        Configuration.getInstance().userAgentValue = context.packageName
    }


    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }


    LaunchedEffect(Unit) {
        val db = AppDatabase.getDatabase(context)
        val alunoDao = db.alunoDao()
        val geocoder = Geocoder(context, Locale.getDefault())


        alunoDao.getAllAlunos().collect { alunos ->


            withContext(Dispatchers.IO) {
                val novosMarcadores = mutableListOf<Marker>()

                alunos.forEach { aluno ->
                    if (aluno.cep.isNotBlank()) {
                        try {

                            val resultados = geocoder.getFromLocationName("${aluno.cep}, Brasil", 1)

                            if (!resultados.isNullOrEmpty()) {
                                val localizacao = resultados[0]
                                val ponto = GeoPoint(localizacao.latitude, localizacao.longitude)


                                withContext(Dispatchers.Main) {
                                    val marcador = Marker(mapView)
                                    marcador.position = ponto
                                    marcador.title = "${aluno.nome} (${aluno.cep})"
                                    marcador.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                    mapView.overlays.add(marcador)
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                withContext(Dispatchers.Main) {
                    mapView.invalidate()
                }
            }
        }
    }

    AndroidView(
        factory = { mapView },
        modifier = Modifier.fillMaxSize()
    )
}