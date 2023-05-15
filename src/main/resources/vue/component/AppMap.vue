<template>
  <div class="card">
    <div id="map"></div>
    <app-popup-form v-if="showForm" @submit="onFormSubmit" />
  </div>

</template>

<script>
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import 'leaflet-contextmenu';
import 'leaflet-contextmenu/dist/leaflet.contextmenu.css';
import {toRaw} from "vue";
import AppPopupForm from './AppPopupForm.vue';



delete L.Icon.Default.prototype._getIconUrl;

L.Icon.Default.mergeOptions({
  iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
  iconUrl: require('leaflet/dist/images/marker-icon.png'),
  shadowUrl: require('leaflet/dist/images/marker-shadow.png')
});

export default {
  data() {
    return {
      pointObjects: [],
      map: null,
      latlng: null,
      showForm: false
    };
  },
  components: {
    AppPopupForm,
  },
  methods: {
    createNewObject(e) {
      this.latlng = e.latlng;
      this.showForm = true;
    },
    onFormSubmit(data) {
      let name = data.name;
      let address = data.address;
      if (name && address && this.latlng) {
        let marker = L.marker(this.latlng).addTo(toRaw(this.map));
        marker.bindPopup(`<b>${name}</b><br>${address}`);
        // Отправка данных маркера на сервер через REST API
        fetch('http://localhost:8081/point-object', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            latitude: this.latlng.lat,
            longitude: this.latlng.lng,
            name: name,
            address: address
          })
        });
      }
      this.showForm = false;
    },
    onMapClick(e) {
      this.latlng = e.latlng;
    }
  },
  mounted() {
    let vm = this;
    this.map = L.map("map", {
      attributionControl: false,
      contextmenu: true,
      contextmenuWidth: 140,
      contextmenuItems: [{
        text: 'Создать новый объект',
        callback: (e) => vm.createNewObject(e)
      }]
    }).setView(
        [54.712, 55.999],
        15
    );

    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
      attribution:
          'Данные карты &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> участники',
      maxZoom: 18,
    }).addTo(toRaw(this.map));

    fetch("http://localhost:8081/point-object")
        .then((response) => response.json())
        .then((data) => {
          this.pointObjects = data;
          this.pointObjects.forEach((pointObject) => {
            let marker = L.marker([
              pointObject.latitude,
              pointObject.longitude,
            ]).addTo(toRaw(this.map));
            marker.bindPopup(
                `<b>${pointObject.name}</b><br>${pointObject.address}`
            );
          });
        });

    this.$nextTick(() => {
      toRaw(this.map).invalidateSize();
    });
  },
};
</script>

<style scoped>

#map {
  height: 100%;
  height: 500px;
  position: relative;
}

.AppPopupForm {
  position: relative;
  z-index: 9999;
}
</style>