document.querySelector('body').onloadend = initMap();
document.querySelector('#btn').onclick = novaConsulta;
document.querySelector('#cidade').onkeypress = function(key) { if (key.keyCode == 13) novaConsulta(key); };

var map;

function initMap() {
    var mapProp = {
        center: {lat: 16.1491733, lng: 1.7820155},
        zoom: 3,
        disableDoubleClickZoom: true,
    };

    map = new google.maps.Map(document.getElementById("map"),mapProp);

    google.maps.event.addListener(map, 'dblclick', function(e) {
        var location = e.latLng.toJSON();

        novaConsulta(null, location);
    });

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {

        setMarker({
          lat: position.coords.latitude,
          lng: position.coords.longitude
        }, true);

        });
    }
}

function novaConsulta(e, location) {

    if (location == null) {
        new google.maps.Geocoder().geocode( {'address': document.getElementById('cidade').value}, function(results, status) {
            if (status == 'OK') 
                setMarker(results[0].geometry.location.toJSON(), true);
        });
    }
    else 
        setMarker(location, false); 
    
}

function setMarker(pos, bool) {
    var result = document.getElementById('result').cloneNode(true)

    obtemDadosTemperatura(result, pos);
    exibeResultados(result, pos, bool);
}

function obtemDadosTemperatura(result, pos) {

    var req = new XMLHttpRequest();

    req.onloadend = function(){

        resp = req.responseXML;

        setDadosTemperatura(resp, result, pos);
    };

    req.open('POST', 'https://api.openweathermap.org/data/2.5/weather?lat=' +
        pos['lat'] + '&lon=' + pos['lng'] + '&units=metric&mode=xml&APPID=63aba212b44019d0f11481a1b6b2d7d3');

    req.send(null);
}

function setDadosTemperatura(dados, result, pos) {

    result.style.display = 'block';
    
    console.log(dados)

    result.childNodes[1].childNodes[0].innerHTML = dados.getElementsByTagName('city')[0].getAttribute('name')
    result.childNodes[3].childNodes[0].setAttribute('src', "http://openweathermap.org/img/w/" + dados.getElementsByTagName('weather')[0].getAttribute('icon') + ".png");
    result.childNodes[5].innerHTML = "Temperatura: " + dados.getElementsByTagName('temperature')[0].getAttribute('value') + " ºC"
    result.childNodes[7].innerHTML = "Temperatura máxima: " + dados.getElementsByTagName('temperature')[0].getAttribute('max') + " ºC"
    result.childNodes[9].innerHTML = "Temperatura minima: " + dados.getElementsByTagName('temperature')[0].getAttribute('min') + " ºC"
    result.childNodes[11].innerHTML = "Latitude: " + pos['lat'];
    result.childNodes[13].innerHTML = "Longitude: " + pos['lng'];

    new google.maps.ElevationService().getElevationForLocations({
        'locations': [pos]
    }, function(results, status) {
        var elevation = results[0].elevation.toString()
        result.childNodes[15].innerHTML = "Altitude: " + elevation.slice(0, elevation.indexOf('.') + 3) + " m";
    });
}

function exibeResultados(result, pos, bool) {

    var marker = new google.maps.Marker({
        position: pos,
        map: map
    });

    var infoWindow = new google.maps.InfoWindow();

    if (bool) {
        map.setCenter(pos);
        map.setZoom(12);
    }

    infoWindow.setContent(result);
    infoWindow.open(map, marker);

    new google.maps.event.addListener(infoWindow,'closeclick',function(){
        marker.setMap(null);
    });
}
