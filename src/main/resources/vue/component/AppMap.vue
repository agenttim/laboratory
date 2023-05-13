<template>
  <div class="card">
    <div id="map"></div>
  </div>
</template>

<script>
import L from "leaflet";
import "leaflet/dist/leaflet.css";

export default {
  data() {
    return {
      pointObjects: [],
    };
  },
  mounted() {
    let map = L.map("map", { attributionControl: false }).setView(
        [54.712, 55.999],
        15
    );

    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
      attribution:
          'Данные карты &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> участники',
      maxZoom: 18,
    }).addTo(map);

    fetch("http://localhost:8081/point-object")
        .then((response) => response.json())
        .then((data) => {
          this.pointObjects = data;
          this.pointObjects.forEach((pointObject) => {
            let marker = L.marker([
              pointObject.latitude,
              pointObject.longitude,
            ]).addTo(map);
            marker.bindPopup(
                `<b>${pointObject.name}</b><br>${pointObject.address}`
            );
          });
        });

    this.$nextTick(() => {
      map.invalidateSize();
    });
  },
};
</script>

<style scoped>
.card {
  height: 500px;
  position: relative;
}

#map {
  height: 100%;
}
</style>