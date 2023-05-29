<template>
  <div class="card">
    <div id="map"></div>
  </div>
</template>

<script>
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import "leaflet-editable"; // подключаем плагин Leaflet.Editable
import axios from "axios"; // подключаем библиотеку axios

export default {
  data() {
    return {
      map: null,
      polygons: [] // переменная для хранения массива площадных объектов
    };
  },
  mounted() {
    this.map = L.map("map", {
      attributionControl: false,
      editable: true // включаем режим редактирования
    }).setView(
        [54.712, 55.999],
        15
    );

    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
      attribution:
          'Данные карты &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> участники',
      maxZoom: 18,
    }).addTo(this.map);

    this.$nextTick(() => {
      this.map.invalidateSize();
    });

    // Отправляем запрос к REST API для получения данных о полигонах
    axios.get("http://localhost:8081/polygons")
        .then(response => {
          this.polygons = response.data; // сохраняем данные в переменную polygons
          // Добавляем многоугольники на карту
          for (let polygon of this.polygons) {
            let latlngs = JSON.parse(polygon.polygon);
            let layer = L.polygon(latlngs).addTo(this.map); // создаем слой с многоугольником

            // Добавляем всплывающее окно с информацией о многоугольнике
            layer.bindPopup(polygon.name);

            // Включаем редактирование многоугольника
            layer.enableEdit();

            // Добавляем обработчик события изменения многоугольника
            layer.on("editable:vertex:dragend", e => {
              // Получаем новые координаты вершин многоугольника
              var newLatlngs = e.layer.getLatLngs()[0];
              // Отправляем новые координаты на сервер через REST API
              axios.put(`http://localhost:8081/polygon/${polygon.id}`, {
                polygon: newLatlngs
              });
            });
          }
        })
        .catch(error => {
          console.error(error); // выводим ошибку в консоль
        });
  }
};
</script>

<style scoped>

#map {
  height: 100%;
  height: 500px;
  position: relative;
}
</style>
