<template>
  <div class="card">
    <div id='map'></div> <!-- создаем элемент div с идентификатором map для отображения карты -->
  </div>
</template>

<script>
import L from 'leaflet'; // импортируем библиотеку leaflet
import 'leaflet/dist/leaflet.css'


export default {
  mounted() {
// создаем объект карты и устанавливаем вид на заданное место и масштаб
    let map = L.map('map', { attributionControl:false }).setView([51.505, -0.09], 13);

    // добавляем слой тайлов на карту
    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
      attribution: 'Данные карты &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> участники',
      maxZoom: 18,
    }).addTo(map);

    // добавляем маркер на карту
    let marker = L.marker([51.5, -0.09]).addTo(map);

    // добавляем всплывающее окно к маркеру
    marker.bindPopup("<b>Привет, мир!</b><br>Я всплывающее окно.").openPopup();

    // обновляем размер карты после рендеринга
    this.$nextTick(() => {
      map.invalidateSize();
    });
  }
}
</script>

<style scoped>
.card {
  /*width: 500px;*/
  height: 500px;
  position: relative;
}

#map {
  height: 100%;
}
</style>