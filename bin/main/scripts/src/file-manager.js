"use strict";
exports.__esModule = true;
exports.FileManager = void 0;
var FileManager = /** @class */ (function () {
    function FileManager() {
        this.mimeMap = this.setMimeMap();
    }
    FileManager.prototype.retrieveDownload = function (attach) {
        var _this = this;
        var xhr = new XMLHttpRequest();
        var url = "http://localhost:2839/download-attachment/" + attach.attachId;
        //sets up ready state handler
        xhr.onreadystatechange = function (xhr) { return _this.cycle(xhr); };
        //opens up the request
        xhr.open("GET", url, true);
        //sends request
        xhr.send();
    };
    FileManager.prototype.cycle = function (xhr) {
        switch (xhr.readyState) {
            case 0:
                console.log("Nothing, initialized not sent");
                break;
            case 1:
                console.log("Connection established");
                break;
            case 2:
                console.log("Request sent");
                break;
            case 3:
                console.log("Waiting response");
                break;
            case 4:
                console.log("Response received");
                //logic to add attachment to table
                if (xhr.status === 200) {
                    var attachData = JSON.parse(xhr.responseText);
                    this.downloadAttach(attachData[0], attachData[1].split(".")[0], "." + attachData[1].split(".")[1]);
                }
                break;
        }
    };
    FileManager.prototype.downloadAttach = function (newData, filename, newType) {
        var mimeType = this.getMimeType(newType);
        var data = this.base64ToArrayBuffer(newData);
        var file = new Blob([data], { type: mimeType });
        if (window.navigator.msSaveOrOpenBlob) // IE10+
            window.navigator.msSaveOrOpenBlob(file, filename);
        else { // Others
            var a = document.createElement("a"), url = URL.createObjectURL(file);
            a.href = url;
            a.download = filename;
            document.body.appendChild(a);
            a.click();
            setTimeout(function () {
                document.body.removeChild(a);
                window.URL.revokeObjectURL(url);
            }, 0);
        }
    };
    FileManager.prototype.base64ToArrayBuffer = function (base64) {
        var binaryString = window.atob(base64);
        var binaryLen = binaryString.length;
        var bytes = new Uint8Array(binaryLen);
        for (var i = 0; i < binaryLen; i++) {
            var ascii = binaryString.charCodeAt(i);
            bytes[i] = ascii;
        }
        return bytes;
    };
    FileManager.prototype.getMimeType = function (type) {
        return this.mimeMap.get(type);
    };
    FileManager.prototype.setMimeMap = function () {
        var mimeMap = new Map();
        mimeMap.set(".323", "text/h323");
        mimeMap.set(".3g2", "video/3gpp2");
        mimeMap.set(".3gp", "video/3gpp");
        mimeMap.set(".3gp2", "video/3gpp2");
        mimeMap.set(".3gpp", "video/3gpp");
        mimeMap.set(".7z", "application/x-7z-compressed");
        mimeMap.set(".aa", "audio/audible");
        mimeMap.set(".AAC", "audio/aac");
        mimeMap.set(".aaf", "application/octet-stream");
        mimeMap.set(".aax", "audio/vnd.audible.aax");
        mimeMap.set(".ac3", "audio/ac3");
        mimeMap.set(".aca", "application/octet-stream");
        mimeMap.set(".accda", "application/msaccess.addin");
        mimeMap.set(".accdb", "application/msaccess");
        mimeMap.set(".accdc", "application/msaccess.cab");
        mimeMap.set(".accde", "application/msaccess");
        mimeMap.set(".accdr", "application/msaccess.runtime");
        mimeMap.set(".accdt", "application/msaccess");
        mimeMap.set(".accdw", "application/msaccess.webapplication");
        mimeMap.set(".accft", "application/msaccess.ftemplate");
        mimeMap.set(".acx", "application/internet-property-stream");
        mimeMap.set(".AddIn", "text/xml");
        mimeMap.set(".ade", "application/msaccess");
        mimeMap.set(".adobebridge", "application/x-bridge-url");
        mimeMap.set(".adp", "application/msaccess");
        mimeMap.set(".ADT", "audio/vnd.dlna.adts");
        mimeMap.set(".ADTS", "audio/aac");
        mimeMap.set(".afm", "application/octet-stream");
        mimeMap.set(".ai", "application/postscript");
        mimeMap.set(".aif", "audio/aiff");
        mimeMap.set(".aifc", "audio/aiff");
        mimeMap.set(".aiff", "audio/aiff");
        mimeMap.set(".air", "application/vnd.adobe.air-application-installer-package+zip");
        mimeMap.set(".amc", "application/mpeg");
        mimeMap.set(".anx", "application/annodex");
        mimeMap.set(".apk", "application/vnd.android.package-archive");
        mimeMap.set(".apng", "image/apng");
        mimeMap.set(".application", "application/x-ms-application");
        mimeMap.set(".art", "image/x-jg");
        mimeMap.set(".asa", "application/xml");
        mimeMap.set(".asax", "application/xml");
        mimeMap.set(".ascx", "application/xml");
        mimeMap.set(".asd", "application/octet-stream");
        mimeMap.set(".asf", "video/x-ms-asf");
        mimeMap.set(".ashx", "application/xml");
        mimeMap.set(".asi", "application/octet-stream");
        mimeMap.set(".asm", "text/plain");
        mimeMap.set(".asmx", "application/xml");
        mimeMap.set(".aspx", "application/xml");
        mimeMap.set(".asr", "video/x-ms-asf");
        mimeMap.set(".asx", "video/x-ms-asf");
        mimeMap.set(".atom", "application/atom+xml");
        mimeMap.set(".au", "audio/basic");
        mimeMap.set(".avci", "image/avci");
        mimeMap.set(".avcs", "image/avcs");
        mimeMap.set(".avi", "video/x-msvideo");
        mimeMap.set(".avif", "image/avif");
        mimeMap.set(".avifs", "image/avif-sequence");
        mimeMap.set(".axa", "audio/annodex");
        mimeMap.set(".axs", "application/olescript");
        mimeMap.set(".axv", "video/annodex");
        mimeMap.set(".bas", "text/plain");
        mimeMap.set(".bcpio", "application/x-bcpio");
        mimeMap.set(".bin", "application/octet-stream");
        mimeMap.set(".bmp", "image/bmp");
        mimeMap.set(".c", "text/plain");
        mimeMap.set(".cab", "application/octet-stream");
        mimeMap.set(".caf", "audio/x-caf");
        mimeMap.set(".calx", "application/vnd.ms-office.calx");
        mimeMap.set(".cat", "application/vnd.ms-pki.seccat");
        mimeMap.set(".cc", "text/plain");
        mimeMap.set(".cd", "text/plain");
        mimeMap.set(".cdda", "audio/aiff");
        mimeMap.set(".cdf", "application/x-cdf");
        mimeMap.set(".cer", "application/x-x509-ca-cert");
        mimeMap.set(".cfg", "text/plain");
        mimeMap.set(".chm", "application/octet-stream");
        mimeMap.set(".class", "application/x-java-applet");
        mimeMap.set(".clp", "application/x-msclip");
        mimeMap.set(".cmd", "text/plain");
        mimeMap.set(".cmx", "image/x-cmx");
        mimeMap.set(".cnf", "text/plain");
        mimeMap.set(".cod", "image/cis-cod");
        mimeMap.set(".config", "application/xml");
        mimeMap.set(".contact", "text/x-ms-contact");
        mimeMap.set(".coverage", "application/xml");
        mimeMap.set(".cpio", "application/x-cpio");
        mimeMap.set(".cpp", "text/plain");
        mimeMap.set(".crd", "application/x-mscardfile");
        mimeMap.set(".crl", "application/pkix-crl");
        mimeMap.set(".crt", "application/x-x509-ca-cert");
        mimeMap.set(".cs", "text/plain");
        mimeMap.set(".csdproj", "text/plain");
        mimeMap.set(".csh", "application/x-csh");
        mimeMap.set(".csproj", "text/plain");
        mimeMap.set(".css", "text/css");
        mimeMap.set(".csv", "text/csv");
        mimeMap.set(".cur", "application/octet-stream");
        mimeMap.set(".czx", "application/x-czx");
        mimeMap.set(".cxx", "text/plain");
        mimeMap.set(".dat", "application/octet-stream");
        mimeMap.set(".datasource", "application/xml");
        mimeMap.set(".dbproj", "text/plain");
        mimeMap.set(".dcr", "application/x-director");
        mimeMap.set(".def", "text/plain");
        mimeMap.set(".deploy", "application/octet-stream");
        mimeMap.set(".der", "application/x-x509-ca-cert");
        mimeMap.set(".dgml", "application/xml");
        mimeMap.set(".dib", "image/bmp");
        mimeMap.set(".dif", "video/x-dv");
        mimeMap.set(".dir", "application/x-director");
        mimeMap.set(".disco", "text/xml");
        mimeMap.set(".divx", "video/divx");
        mimeMap.set(".dll", "application/x-msdownload");
        mimeMap.set(".dll.config", "text/xml");
        mimeMap.set(".dlm", "text/dlm");
        mimeMap.set(".doc", "application/msword");
        mimeMap.set(".docm", "application/vnd.ms-word.document.macroEnabled.12");
        mimeMap.set(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        mimeMap.set(".dot", "application/msword");
        mimeMap.set(".dotm", "application/vnd.ms-word.template.macroEnabled.12");
        mimeMap.set(".dotx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template");
        mimeMap.set(".dsp", "application/octet-stream");
        mimeMap.set(".dsw", "text/plain");
        mimeMap.set(".dtd", "text/xml");
        mimeMap.set(".dtsConfig", "text/xml");
        mimeMap.set(".dv", "video/x-dv");
        mimeMap.set(".dvi", "application/x-dvi");
        mimeMap.set(".dwf", "drawing/x-dwf");
        mimeMap.set(".dwg", "application/acad");
        mimeMap.set(".dwp", "application/octet-stream");
        mimeMap.set(".dxf", "application/x-dxf");
        mimeMap.set(".dxr", "application/x-director");
        mimeMap.set(".eml", "message/rfc822");
        mimeMap.set(".emf", "image/emf");
        mimeMap.set(".emz", "application/octet-stream");
        mimeMap.set(".eot", "application/vnd.ms-fontobject");
        mimeMap.set(".eps", "application/postscript");
        mimeMap.set(".es", "application/ecmascript");
        mimeMap.set(".etl", "application/etl");
        mimeMap.set(".etx", "text/x-setext");
        mimeMap.set(".evy", "application/envoy");
        mimeMap.set(".exe", "application/vnd.microsoft.portable-executable");
        mimeMap.set(".exe.config", "text/xml");
        mimeMap.set(".f4v", "video/mp4");
        mimeMap.set(".fdf", "application/vnd.fdf");
        mimeMap.set(".fif", "application/fractals");
        mimeMap.set(".filters", "application/xml");
        mimeMap.set(".fla", "application/octet-stream");
        mimeMap.set(".flac", "audio/flac");
        mimeMap.set(".flr", "x-world/x-vrml");
        mimeMap.set(".flv", "video/x-flv");
        mimeMap.set(".fsscript", "application/fsharp-script");
        mimeMap.set(".fsx", "application/fsharp-script");
        mimeMap.set(".generictest", "application/xml");
        mimeMap.set(".gif", "image/gif");
        mimeMap.set(".gpx", "application/gpx+xml");
        mimeMap.set(".group", "text/x-ms-group");
        mimeMap.set(".gsm", "audio/x-gsm");
        mimeMap.set(".gtar", "application/x-gtar");
        mimeMap.set(".gz", "application/x-gzip");
        mimeMap.set(".h", "text/plain");
        mimeMap.set(".hdf", "application/x-hdf");
        mimeMap.set(".hdml", "text/x-hdml");
        mimeMap.set(".heic", "image/heic");
        mimeMap.set(".heics", "image/heic-sequence");
        mimeMap.set(".heif", "image/heif");
        mimeMap.set(".heifs", "image/heif-sequence");
        mimeMap.set(".hhc", "application/x-oleobject");
        mimeMap.set(".hhk", "application/octet-stream");
        mimeMap.set(".hhp", "application/octet-stream");
        mimeMap.set(".hlp", "application/winhlp");
        mimeMap.set(".hpp", "text/plain");
        mimeMap.set(".hqx", "application/mac-binhex40");
        mimeMap.set(".hta", "application/hta");
        mimeMap.set(".htc", "text/x-component");
        mimeMap.set(".htm", "text/html");
        mimeMap.set(".html", "text/html");
        mimeMap.set(".htt", "text/webviewhtml");
        mimeMap.set(".hxa", "application/xml");
        mimeMap.set(".hxc", "application/xml");
        mimeMap.set(".hxd", "application/octet-stream");
        mimeMap.set(".hxe", "application/xml");
        mimeMap.set(".hxf", "application/xml");
        mimeMap.set(".hxh", "application/octet-stream");
        mimeMap.set(".hxi", "application/octet-stream");
        mimeMap.set(".hxk", "application/xml");
        mimeMap.set(".hxq", "application/octet-stream");
        mimeMap.set(".hxr", "application/octet-stream");
        mimeMap.set(".hxs", "application/octet-stream");
        mimeMap.set(".hxt", "text/html");
        mimeMap.set(".hxv", "application/xml");
        mimeMap.set(".hxw", "application/octet-stream");
        mimeMap.set(".hxx", "text/plain");
        mimeMap.set(".i", "text/plain");
        mimeMap.set(".ical", "text/calendar");
        mimeMap.set(".icalendar", "text/calendar");
        mimeMap.set(".ico", "image/x-icon");
        mimeMap.set(".ics", "text/calendar");
        mimeMap.set(".idl", "text/plain");
        mimeMap.set(".ief", "image/ief");
        mimeMap.set(".ifb", "text/calendar");
        mimeMap.set(".iii", "application/x-iphone");
        mimeMap.set(".inc", "text/plain");
        mimeMap.set(".inf", "application/octet-stream");
        mimeMap.set(".ini", "text/plain");
        mimeMap.set(".inl", "text/plain");
        mimeMap.set(".ins", "application/x-internet-signup");
        mimeMap.set(".ipa", "application/x-itunes-ipa");
        mimeMap.set(".ipg", "application/x-itunes-ipg");
        mimeMap.set(".ipproj", "text/plain");
        mimeMap.set(".ipsw", "application/x-itunes-ipsw");
        mimeMap.set(".iqy", "text/x-ms-iqy");
        mimeMap.set(".isp", "application/x-internet-signup");
        mimeMap.set(".isma", "application/octet-stream");
        mimeMap.set(".ismv", "application/octet-stream");
        mimeMap.set(".ite", "application/x-itunes-ite");
        mimeMap.set(".itlp", "application/x-itunes-itlp");
        mimeMap.set(".itms", "application/x-itunes-itms");
        mimeMap.set(".itpc", "application/x-itunes-itpc");
        mimeMap.set(".IVF", "video/x-ivf");
        mimeMap.set(".jar", "application/java-archive");
        mimeMap.set(".java", "application/octet-stream");
        mimeMap.set(".jck", "application/liquidmotion");
        mimeMap.set(".jcz", "application/liquidmotion");
        mimeMap.set(".jfif", "image/pjpeg");
        mimeMap.set(".jnlp", "application/x-java-jnlp-file");
        mimeMap.set(".jpb", "application/octet-stream");
        mimeMap.set(".jpe", "image/jpeg");
        mimeMap.set(".jpeg", "image/jpeg");
        mimeMap.set(".jpg", "image/jpeg");
        mimeMap.set(".js", "application/javascript");
        mimeMap.set(".json", "application/json");
        mimeMap.set(".jsx", "text/jscript");
        mimeMap.set(".jsxbin", "text/plain");
        mimeMap.set(".latex", "application/x-latex");
        mimeMap.set(".library-ms", "application/windows-library+xml");
        mimeMap.set(".lit", "application/x-ms-reader");
        mimeMap.set(".loadtest", "application/xml");
        mimeMap.set(".lpk", "application/octet-stream");
        mimeMap.set(".lsf", "video/x-la-asf");
        mimeMap.set(".lst", "text/plain");
        mimeMap.set(".lsx", "video/x-la-asf");
        mimeMap.set(".lzh", "application/octet-stream");
        mimeMap.set(".m13", "application/x-msmediaview");
        mimeMap.set(".m14", "application/x-msmediaview");
        mimeMap.set(".m1v", "video/mpeg");
        mimeMap.set(".m2t", "video/vnd.dlna.mpeg-tts");
        mimeMap.set(".m2ts", "video/vnd.dlna.mpeg-tts");
        mimeMap.set(".m2v", "video/mpeg");
        mimeMap.set(".m3u", "audio/x-mpegurl");
        mimeMap.set(".m3u8", "audio/x-mpegurl");
        mimeMap.set(".m4a", "audio/m4a");
        mimeMap.set(".m4b", "audio/m4b");
        mimeMap.set(".m4p", "audio/m4p");
        mimeMap.set(".m4r", "audio/x-m4r");
        mimeMap.set(".m4v", "video/x-m4v");
        mimeMap.set(".mac", "image/x-macpaint");
        mimeMap.set(".mak", "text/plain");
        mimeMap.set(".man", "application/x-troff-man");
        mimeMap.set(".manifest", "application/x-ms-manifest");
        mimeMap.set(".map", "text/plain");
        mimeMap.set(".master", "application/xml");
        mimeMap.set(".mbox", "application/mbox");
        mimeMap.set(".mda", "application/msaccess");
        mimeMap.set(".mdb", "application/x-msaccess");
        mimeMap.set(".mde", "application/msaccess");
        mimeMap.set(".mdp", "application/octet-stream");
        mimeMap.set(".me", "application/x-troff-me");
        mimeMap.set(".mfp", "application/x-shockwave-flash");
        mimeMap.set(".mht", "message/rfc822");
        mimeMap.set(".mhtml", "message/rfc822");
        mimeMap.set(".mid", "audio/mid");
        mimeMap.set(".midi", "audio/mid");
        mimeMap.set(".mix", "application/octet-stream");
        mimeMap.set(".mk", "text/plain");
        mimeMap.set(".mk3d", "video/x-matroska-3d");
        mimeMap.set(".mka", "audio/x-matroska");
        mimeMap.set(".mkv", "video/x-matroska");
        mimeMap.set(".mmf", "application/x-smaf");
        mimeMap.set(".mno", "text/xml");
        mimeMap.set(".mny", "application/x-msmoney");
        mimeMap.set(".mod", "video/mpeg");
        mimeMap.set(".mov", "video/quicktime");
        mimeMap.set(".movie", "video/x-sgi-movie");
        mimeMap.set(".mp2", "video/mpeg");
        mimeMap.set(".mp2v", "video/mpeg");
        mimeMap.set(".mp3", "audio/mpeg");
        mimeMap.set(".mp4", "video/mp4");
        mimeMap.set(".mp4v", "video/mp4");
        mimeMap.set(".mpa", "video/mpeg");
        mimeMap.set(".mpe", "video/mpeg");
        mimeMap.set(".mpeg", "video/mpeg");
        mimeMap.set(".mpf", "application/vnd.ms-mediapackage");
        mimeMap.set(".mpg", "video/mpeg");
        mimeMap.set(".mpp", "application/vnd.ms-project");
        mimeMap.set(".mpv2", "video/mpeg");
        mimeMap.set(".mqv", "video/quicktime");
        mimeMap.set(".ms", "application/x-troff-ms");
        mimeMap.set(".msg", "application/vnd.ms-outlook");
        mimeMap.set(".msi", "application/octet-stream");
        mimeMap.set(".mso", "application/octet-stream");
        mimeMap.set(".mts", "video/vnd.dlna.mpeg-tts");
        mimeMap.set(".mtx", "application/xml");
        mimeMap.set(".mvb", "application/x-msmediaview");
        mimeMap.set(".mvc", "application/x-miva-compiled");
        mimeMap.set(".mxf", "application/mxf");
        mimeMap.set(".mxp", "application/x-mmxp");
        mimeMap.set(".nc", "application/x-netcdf");
        mimeMap.set(".nsc", "video/x-ms-asf");
        mimeMap.set(".nws", "message/rfc822");
        mimeMap.set(".ocx", "application/octet-stream");
        mimeMap.set(".oda", "application/oda");
        mimeMap.set(".odb", "application/vnd.oasis.opendocument.database");
        mimeMap.set(".odc", "application/vnd.oasis.opendocument.chart");
        mimeMap.set(".odf", "application/vnd.oasis.opendocument.formula");
        mimeMap.set(".odg", "application/vnd.oasis.opendocument.graphics");
        mimeMap.set(".odh", "text/plain");
        mimeMap.set(".odi", "application/vnd.oasis.opendocument.image");
        mimeMap.set(".odl", "text/plain");
        mimeMap.set(".odm", "application/vnd.oasis.opendocument.text-master");
        mimeMap.set(".odp", "application/vnd.oasis.opendocument.presentation");
        mimeMap.set(".ods", "application/vnd.oasis.opendocument.spreadsheet");
        mimeMap.set(".odt", "application/vnd.oasis.opendocument.text");
        mimeMap.set(".oga", "audio/ogg");
        mimeMap.set(".ogg", "audio/ogg");
        mimeMap.set(".ogv", "video/ogg");
        mimeMap.set(".ogx", "application/ogg");
        mimeMap.set(".one", "application/onenote");
        mimeMap.set(".onea", "application/onenote");
        mimeMap.set(".onepkg", "application/onenote");
        mimeMap.set(".onetmp", "application/onenote");
        mimeMap.set(".onetoc", "application/onenote");
        mimeMap.set(".onetoc2", "application/onenote");
        mimeMap.set(".opus", "audio/ogg");
        mimeMap.set(".orderedtest", "application/xml");
        mimeMap.set(".osdx", "application/opensearchdescription+xml");
        mimeMap.set(".otf", "application/font-sfnt");
        mimeMap.set(".otg", "application/vnd.oasis.opendocument.graphics-template");
        mimeMap.set(".oth", "application/vnd.oasis.opendocument.text-web");
        mimeMap.set(".otp", "application/vnd.oasis.opendocument.presentation-template");
        mimeMap.set(".ots", "application/vnd.oasis.opendocument.spreadsheet-template");
        mimeMap.set(".ott", "application/vnd.oasis.opendocument.text-template");
        mimeMap.set(".oxps", "application/oxps");
        mimeMap.set(".oxt", "application/vnd.openofficeorg.extension");
        mimeMap.set(".p10", "application/pkcs10");
        mimeMap.set(".p12", "application/x-pkcs12");
        mimeMap.set(".p7b", "application/x-pkcs7-certificates");
        mimeMap.set(".p7c", "application/pkcs7-mime");
        mimeMap.set(".p7m", "application/pkcs7-mime");
        mimeMap.set(".p7r", "application/x-pkcs7-certreqresp");
        mimeMap.set(".p7s", "application/pkcs7-signature");
        mimeMap.set(".pbm", "image/x-portable-bitmap");
        mimeMap.set(".pcast", "application/x-podcast");
        mimeMap.set(".pct", "image/pict");
        mimeMap.set(".pcx", "application/octet-stream");
        mimeMap.set(".pcz", "application/octet-stream");
        mimeMap.set(".pdf", "application/pdf");
        mimeMap.set(".pfb", "application/octet-stream");
        mimeMap.set(".pfm", "application/octet-stream");
        mimeMap.set(".pfx", "application/x-pkcs12");
        mimeMap.set(".pgm", "image/x-portable-graymap");
        mimeMap.set(".pic", "image/pict");
        mimeMap.set(".pict", "image/pict");
        mimeMap.set(".pkgdef", "text/plain");
        mimeMap.set(".pkgundef", "text/plain");
        mimeMap.set(".pko", "application/vnd.ms-pki.pko");
        mimeMap.set(".pls", "audio/scpls");
        mimeMap.set(".pma", "application/x-perfmon");
        mimeMap.set(".pmc", "application/x-perfmon");
        mimeMap.set(".pml", "application/x-perfmon");
        mimeMap.set(".pmr", "application/x-perfmon");
        mimeMap.set(".pmw", "application/x-perfmon");
        mimeMap.set(".png", "image/png");
        mimeMap.set(".pnm", "image/x-portable-anymap");
        mimeMap.set(".pnt", "image/x-macpaint");
        mimeMap.set(".pntg", "image/x-macpaint");
        mimeMap.set(".pnz", "image/png");
        mimeMap.set(".pot", "application/vnd.ms-powerpoint");
        mimeMap.set(".potm", "application/vnd.ms-powerpoint.template.macroEnabled.12");
        mimeMap.set(".potx", "application/vnd.openxmlformats-officedocument.presentationml.template");
        mimeMap.set(".ppa", "application/vnd.ms-powerpoint");
        mimeMap.set(".ppam", "application/vnd.ms-powerpoint.addin.macroEnabled.12");
        mimeMap.set(".ppm", "image/x-portable-pixmap");
        mimeMap.set(".pps", "application/vnd.ms-powerpoint");
        mimeMap.set(".ppsm", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12");
        mimeMap.set(".ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow");
        mimeMap.set(".ppt", "application/vnd.ms-powerpoint");
        mimeMap.set(".pptm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12");
        mimeMap.set(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        mimeMap.set(".prf", "application/pics-rules");
        mimeMap.set(".prm", "application/octet-stream");
        mimeMap.set(".prx", "application/octet-stream");
        mimeMap.set(".ps", "application/postscript");
        mimeMap.set(".psc1", "application/PowerShell");
        mimeMap.set(".psd", "application/octet-stream");
        mimeMap.set(".psess", "application/xml");
        mimeMap.set(".psm", "application/octet-stream");
        mimeMap.set(".psp", "application/octet-stream");
        mimeMap.set(".pst", "application/vnd.ms-outlook");
        mimeMap.set(".pub", "application/x-mspublisher");
        mimeMap.set(".pwz", "application/vnd.ms-powerpoint");
        mimeMap.set(".qht", "text/x-html-insertion");
        mimeMap.set(".qhtm", "text/x-html-insertion");
        mimeMap.set(".qt", "video/quicktime");
        mimeMap.set(".qti", "image/x-quicktime");
        mimeMap.set(".qtif", "image/x-quicktime");
        mimeMap.set(".qtl", "application/x-quicktimeplayer");
        mimeMap.set(".qxd", "application/octet-stream");
        mimeMap.set(".ra", "audio/x-pn-realaudio");
        mimeMap.set(".ram", "audio/x-pn-realaudio");
        mimeMap.set(".rar", "application/x-rar-compressed");
        mimeMap.set(".ras", "image/x-cmu-raster");
        mimeMap.set(".rat", "application/rat-file");
        mimeMap.set(".rc", "text/plain");
        mimeMap.set(".rc2", "text/plain");
        mimeMap.set(".rct", "text/plain");
        mimeMap.set(".rdlc", "application/xml");
        mimeMap.set(".reg", "text/plain");
        mimeMap.set(".resx", "application/xml");
        mimeMap.set(".rf", "image/vnd.rn-realflash");
        mimeMap.set(".rgb", "image/x-rgb");
        mimeMap.set(".rgs", "text/plain");
        mimeMap.set(".rm", "application/vnd.rn-realmedia");
        mimeMap.set(".rmi", "audio/mid");
        mimeMap.set(".rmp", "application/vnd.rn-rn_music_package");
        mimeMap.set(".rmvb", "application/vnd.rn-realmedia-vbr");
        mimeMap.set(".roff", "application/x-troff");
        mimeMap.set(".rpm", "audio/x-pn-realaudio-plugin");
        mimeMap.set(".rqy", "text/x-ms-rqy");
        mimeMap.set(".rtf", "application/rtf");
        mimeMap.set(".rtx", "text/richtext");
        mimeMap.set(".rvt", "application/octet-stream");
        mimeMap.set(".ruleset", "application/xml");
        mimeMap.set(".s", "text/plain");
        mimeMap.set(".safariextz", "application/x-safari-safariextz");
        mimeMap.set(".scd", "application/x-msschedule");
        mimeMap.set(".scr", "text/plain");
        mimeMap.set(".sct", "text/scriptlet");
        mimeMap.set(".sd2", "audio/x-sd2");
        mimeMap.set(".sdp", "application/sdp");
        mimeMap.set(".sea", "application/octet-stream");
        mimeMap.set(".searchConnector-ms", "application/windows-search-connector+xml");
        mimeMap.set(".setpay", "application/set-payment-initiation");
        mimeMap.set(".setreg", "application/set-registration-initiation");
        mimeMap.set(".settings", "application/xml");
        mimeMap.set(".sgimb", "application/x-sgimb");
        mimeMap.set(".sgml", "text/sgml");
        mimeMap.set(".sh", "application/x-sh");
        mimeMap.set(".shar", "application/x-shar");
        mimeMap.set(".shtml", "text/html");
        mimeMap.set(".sit", "application/x-stuffit");
        mimeMap.set(".sitemap", "application/xml");
        mimeMap.set(".skin", "application/xml");
        mimeMap.set(".skp", "application/x-koan");
        mimeMap.set(".sldm", "application/vnd.ms-powerpoint.slide.macroEnabled.12");
        mimeMap.set(".sldx", "application/vnd.openxmlformats-officedocument.presentationml.slide");
        mimeMap.set(".slk", "application/vnd.ms-excel");
        mimeMap.set(".sln", "text/plain");
        mimeMap.set(".slupkg-ms", "application/x-ms-license");
        mimeMap.set(".smd", "audio/x-smd");
        mimeMap.set(".smi", "application/octet-stream");
        mimeMap.set(".smx", "audio/x-smd");
        mimeMap.set(".smz", "audio/x-smd");
        mimeMap.set(".snd", "audio/basic");
        mimeMap.set(".snippet", "application/xml");
        mimeMap.set(".snp", "application/octet-stream");
        mimeMap.set(".sql", "application/sql");
        mimeMap.set(".sol", "text/plain");
        mimeMap.set(".sor", "text/plain");
        mimeMap.set(".spc", "application/x-pkcs7-certificates");
        mimeMap.set(".spl", "application/futuresplash");
        mimeMap.set(".spx", "audio/ogg");
        mimeMap.set(".src", "application/x-wais-source");
        mimeMap.set(".srf", "text/plain");
        mimeMap.set(".SSISDeploymentManifest", "text/xml");
        mimeMap.set(".ssm", "application/streamingmedia");
        mimeMap.set(".sst", "application/vnd.ms-pki.certstore");
        mimeMap.set(".stl", "application/vnd.ms-pki.stl");
        mimeMap.set(".sv4cpio", "application/x-sv4cpio");
        mimeMap.set(".sv4crc", "application/x-sv4crc");
        mimeMap.set(".svc", "application/xml");
        mimeMap.set(".svg", "image/svg+xml");
        mimeMap.set(".swf", "application/x-shockwave-flash");
        mimeMap.set(".step", "application/step");
        mimeMap.set(".stp", "application/step");
        mimeMap.set(".t", "application/x-troff");
        mimeMap.set(".tar", "application/x-tar");
        mimeMap.set(".tcl", "application/x-tcl");
        mimeMap.set(".testrunconfig", "application/xml");
        mimeMap.set(".testsettings", "application/xml");
        mimeMap.set(".tex", "application/x-tex");
        mimeMap.set(".texi", "application/x-texinfo");
        mimeMap.set(".texinfo", "application/x-texinfo");
        mimeMap.set(".tgz", "application/x-compressed");
        mimeMap.set(".thmx", "application/vnd.ms-officetheme");
        mimeMap.set(".thn", "application/octet-stream");
        mimeMap.set(".tif", "image/tiff");
        mimeMap.set(".tiff", "image/tiff");
        mimeMap.set(".tlh", "text/plain");
        mimeMap.set(".tli", "text/plain");
        mimeMap.set(".toc", "application/octet-stream");
        mimeMap.set(".tr", "application/x-troff");
        mimeMap.set(".trm", "application/x-msterminal");
        mimeMap.set(".trx", "application/xml");
        mimeMap.set(".ts", "video/vnd.dlna.mpeg-tts");
        mimeMap.set(".tsv", "text/tab-separated-values");
        mimeMap.set(".ttf", "application/font-sfnt");
        mimeMap.set(".tts", "video/vnd.dlna.mpeg-tts");
        mimeMap.set(".txt", "text/plain");
        mimeMap.set(".u32", "application/octet-stream");
        mimeMap.set(".uls", "text/iuls");
        mimeMap.set(".user", "text/plain");
        mimeMap.set(".ustar", "application/x-ustar");
        mimeMap.set(".vb", "text/plain");
        mimeMap.set(".vbdproj", "text/plain");
        mimeMap.set(".vbk", "video/mpeg");
        mimeMap.set(".vbproj", "text/plain");
        mimeMap.set(".vbs", "text/vbscript");
        mimeMap.set(".vcf", "text/x-vcard");
        mimeMap.set(".vcproj", "application/xml");
        mimeMap.set(".vcs", "text/plain");
        mimeMap.set(".vcxproj", "application/xml");
        mimeMap.set(".vddproj", "text/plain");
        mimeMap.set(".vdp", "text/plain");
        mimeMap.set(".vdproj", "text/plain");
        mimeMap.set(".vdx", "application/vnd.ms-visio.viewer");
        mimeMap.set(".vml", "text/xml");
        mimeMap.set(".vscontent", "application/xml");
        mimeMap.set(".vsct", "text/xml");
        mimeMap.set(".vsd", "application/vnd.visio");
        mimeMap.set(".vsi", "application/ms-vsi");
        mimeMap.set(".vsix", "application/vsix");
        mimeMap.set(".vsixlangpack", "text/xml");
        mimeMap.set(".vsixmanifest", "text/xml");
        mimeMap.set(".vsmdi", "application/xml");
        mimeMap.set(".vspscc", "text/plain");
        mimeMap.set(".vss", "application/vnd.visio");
        mimeMap.set(".vsscc", "text/plain");
        mimeMap.set(".vssettings", "text/xml");
        mimeMap.set(".vssscc", "text/plain");
        mimeMap.set(".vst", "application/vnd.visio");
        mimeMap.set(".vstemplate", "text/xml");
        mimeMap.set(".vsto", "application/x-ms-vsto");
        mimeMap.set(".vsw", "application/vnd.visio");
        mimeMap.set(".vsx", "application/vnd.visio");
        mimeMap.set(".vtt", "text/vtt");
        mimeMap.set(".vtx", "application/vnd.visio");
        mimeMap.set(".wasm", "application/wasm");
        mimeMap.set(".wav", "audio/wav");
        mimeMap.set(".wave", "audio/wav");
        mimeMap.set(".wax", "audio/x-ms-wax");
        mimeMap.set(".wbk", "application/msword");
        mimeMap.set(".wbmp", "image/vnd.wap.wbmp");
        mimeMap.set(".wcm", "application/vnd.ms-works");
        mimeMap.set(".wdb", "application/vnd.ms-works");
        mimeMap.set(".wdp", "image/vnd.ms-photo");
        mimeMap.set(".webarchive", "application/x-safari-webarchive");
        mimeMap.set(".webm", "video/webm");
        mimeMap.set(".webp", "image/webp"); /* https://en.wikipedia.org/wiki/WebP */
        mimeMap.set(".webtest", "application/xml");
        mimeMap.set(".wiq", "application/xml");
        mimeMap.set(".wiz", "application/msword");
        mimeMap.set(".wks", "application/vnd.ms-works");
        mimeMap.set(".WLMP", "application/wlmoviemaker");
        mimeMap.set(".wlpginstall", "application/x-wlpg-detect");
        mimeMap.set(".wlpginstall3", "application/x-wlpg3-detect");
        mimeMap.set(".wm", "video/x-ms-wm");
        mimeMap.set(".wma", "audio/x-ms-wma");
        mimeMap.set(".wmd", "application/x-ms-wmd");
        mimeMap.set(".wmf", "application/x-msmetafile");
        mimeMap.set(".wml", "text/vnd.wap.wml");
        mimeMap.set(".wmlc", "application/vnd.wap.wmlc");
        mimeMap.set(".wmls", "text/vnd.wap.wmlscript");
        mimeMap.set(".wmlsc", "application/vnd.wap.wmlscriptc");
        mimeMap.set(".wmp", "video/x-ms-wmp");
        mimeMap.set(".wmv", "video/x-ms-wmv");
        mimeMap.set(".wmx", "video/x-ms-wmx");
        mimeMap.set(".wmz", "application/x-ms-wmz");
        mimeMap.set(".woff", "application/font-woff");
        mimeMap.set(".woff2", "application/font-woff2");
        mimeMap.set(".wpl", "application/vnd.ms-wpl");
        mimeMap.set(".wps", "application/vnd.ms-works");
        mimeMap.set(".wri", "application/x-mswrite");
        mimeMap.set(".wrl", "x-world/x-vrml");
        mimeMap.set(".wrz", "x-world/x-vrml");
        mimeMap.set(".wsc", "text/scriptlet");
        mimeMap.set(".wsdl", "text/xml");
        mimeMap.set(".wvx", "video/x-ms-wvx");
        mimeMap.set(".x", "application/directx");
        mimeMap.set(".xaf", "x-world/x-vrml");
        mimeMap.set(".xaml", "application/xaml+xml");
        mimeMap.set(".xap", "application/x-silverlight-app");
        mimeMap.set(".xbap", "application/x-ms-xbap");
        mimeMap.set(".xbm", "image/x-xbitmap");
        mimeMap.set(".xdr", "text/plain");
        mimeMap.set(".xht", "application/xhtml+xml");
        mimeMap.set(".xhtml", "application/xhtml+xml");
        mimeMap.set(".xla", "application/vnd.ms-excel");
        mimeMap.set(".xlam", "application/vnd.ms-excel.addin.macroEnabled.12");
        mimeMap.set(".xlc", "application/vnd.ms-excel");
        mimeMap.set(".xld", "application/vnd.ms-excel");
        mimeMap.set(".xlk", "application/vnd.ms-excel");
        mimeMap.set(".xll", "application/vnd.ms-excel");
        mimeMap.set(".xlm", "application/vnd.ms-excel");
        mimeMap.set(".xls", "application/vnd.ms-excel");
        mimeMap.set(".xlsb", "application/vnd.ms-excel.sheet.binary.macroEnabled.12");
        mimeMap.set(".xlsm", "application/vnd.ms-excel.sheet.macroEnabled.12");
        mimeMap.set(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        mimeMap.set(".xlt", "application/vnd.ms-excel");
        mimeMap.set(".xltm", "application/vnd.ms-excel.template.macroEnabled.12");
        mimeMap.set(".xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template");
        mimeMap.set(".xlw", "application/vnd.ms-excel");
        mimeMap.set(".xml", "text/xml");
        mimeMap.set(".xmp", "application/octet-stream");
        mimeMap.set(".xmta", "application/xml");
        mimeMap.set(".xof", "x-world/x-vrml");
        mimeMap.set(".XOML", "text/plain");
        mimeMap.set(".xpm", "image/x-xpixmap");
        mimeMap.set(".xps", "application/vnd.ms-xpsdocument");
        mimeMap.set(".xrm-ms", "text/xml");
        mimeMap.set(".xsc", "application/xml");
        mimeMap.set(".xsd", "text/xml");
        mimeMap.set(".xsf", "text/xml");
        mimeMap.set(".xsl", "text/xml");
        mimeMap.set(".xslt", "text/xml");
        mimeMap.set(".xsn", "application/octet-stream");
        mimeMap.set(".xss", "application/xml");
        mimeMap.set(".xspf", "application/xspf+xml");
        mimeMap.set(".xtp", "application/octet-stream");
        mimeMap.set(".xwd", "image/x-xwindowdump");
        mimeMap.set(".z", "application/x-compress");
        mimeMap.set(".zip", "application/zip");
        return mimeMap;
    };
    return FileManager;
}());
exports.FileManager = FileManager;
