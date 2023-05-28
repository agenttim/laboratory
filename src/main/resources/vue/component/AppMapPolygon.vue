<template>
  <div class="card">
    <div id="map"></div>
  </div>

</template>

<script>
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import "leaflet-editable"; // подключаем плагин Leaflet.Editable

export default {
  data() {
    return {
      map: null,
      polygons: [ // переменная для хранения массива площадных объектов
        // Создаем координаты вершин многоугольников
        [
          [54.712, 55.999],
          [54.713, 56.001],
          [54.711, 56.002],
          [54.710, 56.000]
        ],
        [
          [54.714, 56.003],
          [54.715, 56.005],
          [54.713, 56.006],
          [54.712, 56.004],
            [54.715, 56.001]
        ],
        [
          [54.710, 55.997],
          [54.711, 55.999],
          [54.709, 56.000],
          [54.708, 55.998]
        ]
      ]
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

    // Добавляем многоугольники на карту
    for (let latlngs of this.polygons) {
      let polygon = L.polygon(latlngs).addTo(this.map);

      // Добавляем всплывающее окно с информацией о многоугольнике
      polygon.bindPopup("Это площадной объект");

      // Включаем редактирование многоугольника
      polygon.enableEdit();

      // Добавляем обработчик события изменения многоугольника
      polygon.on("editable:vertex:dragend", e => {
        // Получаем новые координаты вершин многоугольника
        var newLatlngs = e.layer.getLatLngs()[0];
        // Отправляем новые координаты на сервер через REST API
        fetch('http://localhost:8081/polygon', {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(newLatlngs)
        });
      });
    }

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