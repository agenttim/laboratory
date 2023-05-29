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
      editable: true // включаем режим редактирования
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
          for (let polygon of this.polygons) {
            L.polygon(polygon.polygon.split("),(").slice(1, -1).map((coords) => coords.split(",").map((num) => parseFloat(num))), { color: polygon.color }).addTo(this.map);
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
