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
    get: function (url, params, callback) {

        var _requestParams = [];
        var keys = Object.keys(params)
        for(var key in keys) {
            _requestParams.push(keys[key] + "=" + params[keys[key]]);
        }
        var requestParams = _requestParams.join("&");

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
               // Typical action to be performed when the document is ready:
               callback(this.responseText);
            }
        };

        var finalUrl = url + "?" + requestParams;

        xhttp.open("GET", finalUrl , true);
        xhttp.send();
    }
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

function error(message) {
    debug(message)
}

var streamKey = getUrlParameter('streamKey');
var livestreamServiceUrl = "https://app.kloudlearn.com/kl-fs-backend/index.php";
function initialize() {
    debug("Livestream Key: " + streamKey)
    if(streamKey !== null && streamKey !== undefined)
        http.get(livestreamServiceUrl, { name: "asdas" }, setupPlayer);
    else
        error("Cannot find stream")
}

initialize()
