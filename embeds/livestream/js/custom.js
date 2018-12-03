var player = "livestream-player";

function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

var debugEnabled = getUrlParameter('debug')

function debug(that) {
    if(debugEnabled === "true") {
        console.debug(that);
    }
}

var http = {
    get: function (url, params, onSuccess, onError) {

        var _requestParams = [];
        var keys = Object.keys(params)
        for(var key in keys) {
            _requestParams.push(keys[key] + "=" + params[keys[key]]);
        }
        var requestParams = _requestParams.join("&");

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState === 4) {
               // Typical action to be performed when the document is ready:
               if(this.status === 200) {
                    onSuccess(this.responseText);
               } else if(this.status === 404) {
                    onError("Not found", 404)
               } else {
                    onError(this.responseText, 500)
               }
            }
        };

        var finalUrl = url + "?" + requestParams;

        xhttp.open("GET", finalUrl , true);
        xhttp.send();
    }
}

function isJson(item) {
    item = typeof item !== "string"
        ? JSON.stringify(item)
        : item;

    try {
        item = JSON.parse(item);
    } catch (e) {
        return false;
    }

    if (typeof item === "object" && item !== null) {
        return true;
    }

    return false;
}

function setupPlayer(data) {
    debug(data)
    var livestreamElementId = "livestream-player";
    var player = videojs(livestreamElementId);

    player.src({
        src: "https://d2zihajmogu5jn.cloudfront.net/bipbop-advanced/bipbop_16x9_variant.m3u8",
        type: "application/x-mpegURL"
    })
}

function handleError(message, status) {
    var errorMessage = "Error playing video";
    if(isJson(message)) {
        var errorJson = JSON.parse(message)
        if(errorJson['detail'] !== undefined) {
            errorMessage = errorJson['detail']
        }
    }
    var errorElement = document.getElementById("error-message");
    errorElement.innerText = errorMessage;
    var errorOverlay = document.getElementById("error-overlay-element")
    errorOverlay.classList.add('show')
}

var streamKey = getUrlParameter('streamKey');
var livestreamServiceUrl = "http://localhost:8084/api/livestreams/embed";
function initialize() {
    debug("Livestream Key: " + streamKey)
    if(streamKey !== null && streamKey !== undefined)
        http.get(livestreamServiceUrl, { streamKey: streamKey}, setupPlayer, handleError);
    else
        error("Cannot find stream")
}

initialize()
