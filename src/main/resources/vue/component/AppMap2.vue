<template>
  <div>
    <div id="map"></div>
  </div>
</template>

<script>
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import "leaflet-editable"; // подключаем плагин Leaflet.Editable

export default {
  name: "AppMap2",
  data() {
    return {
      url: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
      attribution:
          '© <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a>',
      zoom: 15,
      center: [54.713, 55.999],
      polygons: [],
      map: null,
    };
  },
  mounted() {
    // Create a map instance
    this.map = L.map("map",{
      attributionControl: false,
      editable: true, // включаем режим редактирования
    }).setView(this.center, this.zoom);

    // Add a tile layer to the map
    L.tileLayer(this.url, { attribution: this.attribution }).addTo(this.map);

    // Fetch the polygons data from the server
    fetch("http://localhost:8081/polygons")
        .then((response) => response.json())
        .then((data) => {
          // Store the data in the polygons property
          this.polygons = data;

          // Add the polygons to the map
          let selectedLayer = null; // переменная для хранения выбранного слоя

          for (let polygon of this.polygons) {
            // Преобразуем строку в массив координат
            let coords = polygon.polygon.slice(2, -2).split("),(").map((coords) => coords.split(",").map((num) => parseFloat(num)));
            // Выводим массив координат в консоль
            console.log(coords);
            let layer = L.polygon(coords, { color: polygon.color }).addTo(this.map);

            // Добавляем обработчик события click для каждого слоя
            layer.on('click', (e) => {
              // Проверяем, есть ли уже выбранный слой
              if (selectedLayer) {
                // Отключаем режим редактирования для предыдущего слоя
                selectedLayer.disableEdit();
              }
              // Включаем режим редактирования для текущего слоя
              e.target.enableEdit();
              // Обновляем значение переменной selectedLayer на текущий слой
              selectedLayer = e.target;
            });

            layer.on('editable:vertex:dragend', (e) => {
              // Получаем новые координаты фигуры
              let newCoords = e.target.getLatLngs()[0].map((latlng) => [latlng.lat.toFixed(6), latlng.lng.toFixed(6)]);
              newCoords = "((" + newCoords.join("),(") + "))"
              console.log(newCoords)
              // Отправляем их на сервер с помощью fetch
              fetch(`http://localhost:8081/polygons/${polygon.id}`, {
                method: 'PUT',
                headers: {
                  'Content-Type': 'application/json'
                },
                body: JSON.stringify({polygon: newCoords})
              })
                  .then((response) => response.json())
                  .then((data) => {
                    console.log(data);
                  })
                  .catch((error) => {
                    console.error(error);
                  });
            });

          }
        })
        .catch((error) => {
          console.error(error);
        });
  },
};
</script>

<style scoped>
#map {
  height: 600px;
}
</style>
