package com.capstoneproject.app;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

public class Coordinates
{
    private PolygonOptions griffinHall = new PolygonOptions()
            .add(new LatLng(39.031175, -84.466919),
                 new LatLng(39.031180, -84.466794),
                 new LatLng(39.031262, -84.466788),
                 new LatLng(39.031265, -84.466648),
                 new LatLng(39.030978, -84.466653),
                 new LatLng(39.030976, -84.466581),
                 new LatLng(39.031014, -84.466563),
                 new LatLng(39.031058, -84.466529),
                 new LatLng(39.031078, -84.466484),
                 new LatLng(39.031091, -84.466422),
                 new LatLng(39.031076, -84.466349),
                 new LatLng(39.031059, -84.466315),
                 new LatLng(39.031030, -84.466279),
                 new LatLng(39.031003, -84.466254),
                 new LatLng(39.030969, -84.466231),
                 new LatLng(39.030915, -84.466224),
                 new LatLng(39.030856, -84.466249),
                 new LatLng(39.030817, -84.466290),
                 new LatLng(39.030800, -84.466324),
                 new LatLng(39.030783, -84.466348),
                 new LatLng(39.030778, -84.466397),
                 new LatLng(39.030741, -84.466374),
                 new LatLng(39.030673, -84.466385),
                 new LatLng(39.030677, -84.466300),
                 new LatLng(39.030578, -84.466283),
                 new LatLng(39.030559, -84.466689),
                 new LatLng(39.030595, -84.466688),
                 new LatLng(39.030590, -84.466773),
                 new LatLng(39.030550, -84.466782),
                 new LatLng(39.030555, -84.466917)).strokeColor(Color.BLACK).fillColor(0x7F636363);

    private PolygonOptions scienceBuilding = new PolygonOptions()
            .add(new LatLng(39.032554, -84.466865),
                new LatLng(39.032562, -84.466407),
                new LatLng(39.032603, -84.466407),
                new LatLng(39.032606, -84.466093),
                new LatLng(39.032561, -84.466096),
                new LatLng(39.032560, -84.465615),
                new LatLng(39.032461, -84.465612),
                new LatLng(39.032466, -84.465571),
                new LatLng(39.032434, -84.465564),
                new LatLng(39.032433, -84.465700),
                new LatLng(39.032451, -84.465700),
                new LatLng(39.032448, -84.465719),
                new LatLng(39.032386, -84.465722),
                new LatLng(39.032375, -84.466082),
                new LatLng(39.032351, -84.466081),
                new LatLng(39.032356, -84.466046),
                new LatLng(39.032279, -84.466039),
                new LatLng(39.032274, -84.465851),
                new LatLng(39.032239, -84.465854),
                new LatLng(39.032238, -84.465930),
                new LatLng(39.032226, -84.465917),
                new LatLng(39.032166, -84.465917),
                new LatLng(39.032144, -84.465929),
                new LatLng(39.032124, -84.465957),
                new LatLng(39.032121, -84.465996),
                new LatLng(39.032137, -84.466053),
                new LatLng(39.032117, -84.466072),
                new LatLng(39.032116, -84.466306),
                new LatLng(39.032134, -84.466310),
                new LatLng(39.032135, -84.466368),
                new LatLng(39.032127, -84.466487),
                new LatLng(39.032173, -84.466556),
                new LatLng(39.032230, -84.466554),
                new LatLng(39.032239, -84.466629),
                new LatLng(39.032272, -84.466630),
                new LatLng(39.032274, -84.466402),
                new LatLng(39.032297, -84.466399),
                new LatLng(39.032304, -84.466432),
                new LatLng(39.032354, -84.466433),
                new LatLng(39.032369, -84.466398),
                new LatLng(39.032382, -84.466895),
                new LatLng(39.032440, -84.466906),
                new LatLng(39.032445, -84.466935),
                new LatLng(39.032480, -84.466935),
                new LatLng(39.032482, -84.466955),
                new LatLng(39.032515, -84.466955),
                new LatLng(39.032516, -84.466871)).strokeColor(Color.BLACK).fillColor(0x7F636363);

    private PolygonOptions landrumHall = new PolygonOptions()
            .add(new LatLng(39.032422, -84.464614),
                new LatLng(39.032281, -84.464797),
                new LatLng(39.032364, -84.464908),
                new LatLng(39.032339, -84.464939),
                new LatLng(39.032383, -84.465003),
                new LatLng(39.032411, -84.464978),
                new LatLng(39.03245, -84.465025),
                new LatLng(39.032578, -84.464861),
                new LatLng(39.032561, -84.464839),
                new LatLng(39.032567, -84.464681),
                new LatLng(39.032597, -84.464683),
                new LatLng(39.032597, -84.464147),
                new LatLng(39.032556, -84.464144),
                new LatLng(39.032556, -84.464114),
                new LatLng(39.032489, -84.464111),
                new LatLng(39.032489, -84.464147),
                new LatLng(39.032428, -84.464142)).strokeColor(Color.BLACK).fillColor(0x7F636363);

    private PolygonOptions fineArts = new PolygonOptions()
            .add(new LatLng(39.030773, -84.463538),
                new LatLng(39.030977, -84.463543),
                new LatLng(39.030995, -84.463477),
                new LatLng(39.030980, -84.463452),
                new LatLng(39.031087, -84.463318),
                new LatLng(39.031102, -84.463339),
                new LatLng(39.031121, -84.463316),
                new LatLng(39.031165, -84.463320),
                new LatLng(39.031180, -84.463340),
                new LatLng(39.031199, -84.463322),
                new LatLng(39.031214, -84.463338),
                new LatLng(39.031258, -84.463284),
                new LatLng(39.031290, -84.463303),
                new LatLng(39.031317, -84.463223),
                new LatLng(39.031450, -84.463227),
                new LatLng(39.031451, -84.463276),
                new LatLng(39.031512, -84.463284),
                new LatLng(39.031516, -84.463249),
                new LatLng(39.031667, -84.463247),
                new LatLng(39.031670, -84.463333),
                new LatLng(39.031662, -84.463447),
                new LatLng(39.031627, -84.463559),
                new LatLng(39.031574, -84.463659),
                new LatLng(39.031524, -84.463706),
                new LatLng(39.031494, -84.463726),
                new LatLng(39.031490, -84.463791),
                new LatLng(39.031612, -84.463793),
                new LatLng(39.031619, -84.464054),
                new LatLng(39.031656, -84.464050),
                new LatLng(39.031650, -84.464208),
                new LatLng(39.031472, -84.464211),
                new LatLng(39.031608, -84.464388),
                new LatLng(39.031507, -84.464476),
                new LatLng(39.031360, -84.464250),
                new LatLng(39.031347, -84.464217),
                new LatLng(39.031314, -84.464211),
                new LatLng(39.031173, -84.464038),
                new LatLng(39.031147, -84.464063),
                new LatLng(39.031117, -84.464057),
                new LatLng(39.030974, -84.463857),
                new LatLng(39.030773, -84.463853)).strokeColor(Color.BLACK).fillColor(0x7F636363);

    private PolygonOptions albrightHealthCenter = new PolygonOptions()
            .add(new LatLng(39.029443, -84.466784),
                    new LatLng(39.029451, -84.466229),
                    new LatLng(39.029389, -84.466236),
                    new LatLng(39.029402, -84.466128),
                    new LatLng(39.029527, -84.466130),
                    new LatLng(39.029529, -84.465975),
                    new LatLng(39.029479, -84.465975),
                    new LatLng(39.029485, -84.465599),
                    new LatLng(39.029450, -84.465595),
                    new LatLng(39.029347, -84.465591),
                    new LatLng(39.029348, -84.465556),
                    new LatLng(39.029319, -84.465560),
                    new LatLng(39.029313, -84.465591),
                    new LatLng(39.029154, -84.465584),
                    new LatLng(39.029155, -84.465531),
                    new LatLng(39.028887, -84.465535),
                    new LatLng(39.028886, -84.466218),
                    new LatLng(39.028808, -84.466232),
                    new LatLng(39.028811, -84.466779)).strokeColor(Color.BLACK).fillColor(0x7F636363);

    private PolygonOptions baptistStudentUnion = new PolygonOptions()
            .add(new LatLng(39.033402, -84.464325),
                new LatLng(39.033332, -84.464437),
                new LatLng(39.033364, -84.464463),
                new LatLng(39.033271, -84.464620),
                new LatLng(39.033206, -84.464560),
                new LatLng(39.033197, -84.464574),
                new LatLng(39.033099, -84.464485),
                new LatLng(39.033111, -84.464464),
                new LatLng(39.033044, -84.464402),
                new LatLng(39.033130, -84.464251),
                new LatLng(39.033160, -84.464272),
                new LatLng(39.033224, -84.464161)).strokeColor(Color.BLACK).fillColor(0x7F636363);

    private PolygonOptions mathEducationPsychologyCenter = new PolygonOptions()
            .add(new LatLng(39.029908, -84.463128),
                new LatLng(39.030032, -84.463127),
                new LatLng(39.030030, -84.463047),
                new LatLng(39.030127, -84.463036),
                new LatLng(39.030141, -84.462498),
                new LatLng(39.030173, -84.462494),
                new LatLng(39.030181, -84.462579),
                new LatLng(39.030391, -84.462633),
                new LatLng(39.030464, -84.462436),
                new LatLng(39.030287, -84.462269),
                new LatLng(39.030238, -84.462317),
                new LatLng(39.030218, -84.462291),
                new LatLng(39.030515, -84.461912),
                new LatLng(39.030462, -84.461819),
                new LatLng(39.030462, -84.461755),
                new LatLng(39.030395, -84.461662),
                new LatLng(39.030005, -84.462146),
                new LatLng(39.029943, -84.462272),
                new LatLng(39.029901, -84.462405),
                new LatLng(39.029904, -84.463130)).strokeColor(Color.BLACK).fillColor(0x7F636363);

    private PolygonOptions businessAcademicCenter = new PolygonOptions()
            .add(new LatLng(39.030544, -84.461444),
                new LatLng(39.030547, -84.461669),
                new LatLng(39.030642, -84.461778),
                new LatLng(39.031253, -84.461786),
                new LatLng(39.031406, -84.461992),
                new LatLng(39.03145, -84.461994),
                new LatLng(39.031583, -84.461833),
                new LatLng(39.031586, -84.461761),
                new LatLng(39.031369, -84.461475),
                new LatLng(39.031189, -84.461461),
                new LatLng(39.031181, -84.461353),
                new LatLng(39.030731, -84.461349),
                new LatLng(39.030744, -84.461458)).strokeColor(Color.BLACK).fillColor(0x7F636363);

    private PolygonOptions lucasAdministrativeCenter = new PolygonOptions()
            .add(new LatLng(39.029481, -84.463652),
                new LatLng(39.029807, -84.463651),
                new LatLng(39.029810, -84.463455),
                new LatLng(39.029831, -84.463453),
                new LatLng(39.029833, -84.463402),
                new LatLng(39.029779, -84.463400),
                new LatLng(39.029779, -84.463305),
                new LatLng(39.029457, -84.463300),
                new LatLng(39.029452, -84.463504),
                new LatLng(39.029419, -84.463500),
                new LatLng(39.029421, -84.463555),
                new LatLng(39.029477, -84.463560)).strokeColor(Color.BLACK).fillColor(0x7F636363);

    private PolygonOptions foundersHall = new PolygonOptions()
            .add(new LatLng(39.032054, -84.465296),
                new LatLng(39.031970, -84.465188),
                new LatLng(39.031990, -84.465162),
                new LatLng(39.031929, -84.465079),
                new LatLng(39.031907, -84.465100),
                new LatLng(39.031880, -84.465067),
                new LatLng(39.031896, -84.465041),
                new LatLng(39.031832, -84.464958),
                new LatLng(39.031816, -84.464971),
                new LatLng(39.031572, -84.464658),
                new LatLng(39.031386, -84.464923),
                new LatLng(39.031449, -84.464993),
                new LatLng(39.031436, -84.465014),
                new LatLng(39.031493, -84.465097),
                new LatLng(39.031515, -84.465084),
                new LatLng(39.031766, -84.465412),
                new LatLng(39.031731, -84.465460),
                new LatLng(39.031849, -84.465623),
                new LatLng(39.031997, -84.465433),
                new LatLng(39.031977, -84.465408)).strokeColor(Color.BLACK).fillColor(0x7F636363);

    private PolygonOptions nunnHall = new PolygonOptions()
            .add(new LatLng(39.030575, -84.464974),
                new LatLng(39.030686, -84.464976),
                new LatLng(39.030686, -84.465033),
                new LatLng(39.030741, -84.465039),
                new LatLng(39.030749, -84.464980),
                new LatLng(39.031220, -84.464980),
                new LatLng(39.031231, -84.464689),
                new LatLng(39.031130, -84.464684),
                new LatLng(39.031125, -84.464633),
                new LatLng(39.031038, -84.464630),
                new LatLng(39.031045, -84.464678),
                new LatLng(39.030583, -84.464678)).strokeColor(Color.BLACK).fillColor(0x7F636363);

    private PolygonOptions regentsHall = new PolygonOptions()
            .add(new LatLng(39.029486, -84.465594),
                new LatLng(39.029457, -84.465592),
                new LatLng(39.029457, -84.465295),
                new LatLng(39.029535, -84.465297),
                new LatLng(39.029648, -84.465257),
                new LatLng(39.029646, -84.465068),
                new LatLng(39.029547, -84.465064),
                new LatLng(39.029531, -84.464784),
                new LatLng(39.029544, -84.464686),
                new LatLng(39.029487, -84.464691),
                new LatLng(39.029481, -84.464666),
                new LatLng(39.029237, -84.464674),
                new LatLng(39.029237, -84.464685),
                new LatLng(39.029180, -84.464686),
                new LatLng(39.029187, -84.464786),
                new LatLng(39.029170, -84.464782),
                new LatLng(39.029176, -84.465287),
                new LatLng(39.029197, -84.465300),
                new LatLng(39.029201, -84.465580)).strokeColor(Color.BLACK).fillColor(0x7F636363);

    private PolygonOptions studentUnion = new PolygonOptions()
            .add(new LatLng(39.029928, -84.465838),
                new LatLng(39.030170, -84.465897),
                new LatLng(39.030330, -84.465898),
                new LatLng(39.030334, -84.465924),
                new LatLng(39.030428, -84.465928),
                new LatLng(39.030473, -84.465914),
                new LatLng(39.030480, -84.465657),
                new LatLng(39.030376, -84.465652),
                new LatLng(39.030375, -84.465547),
                new LatLng(39.030476, -84.465537),
                new LatLng(39.030482, -84.465258),
                new LatLng(39.030406, -84.465247),
                new LatLng(39.030390, -84.465019),
                new LatLng(39.030358, -84.465009),
                new LatLng(39.030370, -84.464942),
                new LatLng(39.030485, -84.464937),
                new LatLng(39.030493, -84.464656),
                new LatLng(39.030382, -84.464650),
                new LatLng(39.030374, -84.464630),
                new LatLng(39.029946, -84.464624),
                new LatLng(39.029950, -84.464644),
                new LatLng(39.029887, -84.464649),
                new LatLng(39.029890, -84.464911),
                new LatLng(39.029921, -84.464921),
                new LatLng(39.029911, -84.465283),
                new LatLng(39.029943, -84.465283)).strokeColor(Color.BLACK).fillColor(0x7F636363);

    private PolygonOptions steelyLibrary = new PolygonOptions()
            .add(new LatLng(39.032049, -84.463232),
                new LatLng(39.032186, -84.463407),
                new LatLng(39.032210, -84.463381),
                new LatLng(39.032259, -84.463442),
                new LatLng(39.032239, -84.463473),
                new LatLng(39.032276, -84.463526),
                new LatLng(39.032035, -84.463832),
                new LatLng(39.032033, -84.463920),
                new LatLng(39.032107, -84.464018),
                new LatLng(39.031998, -84.464158),
                new LatLng(39.032089, -84.464257),
                new LatLng(39.031749, -84.464246),
                new LatLng(39.031754, -84.464132),
                new LatLng(39.031706, -84.464130),
                new LatLng(39.031700, -84.464015),
                new LatLng(39.031683, -84.464011),
                new LatLng(39.031684, -84.463759),
                new LatLng(39.031699, -84.463758),
                new LatLng(39.031705, -84.463675),
                new LatLng(39.031782, -84.463672),
                new LatLng(39.031762, -84.463672)).strokeColor(Color.BLACK).fillColor(0x7F636363);

    private PolygonOptions universityCenter = new PolygonOptions()
            .add(new LatLng(39.030002, -84.464518),
                new LatLng(39.030276, -84.464521),
                new LatLng(39.030280, -84.464125),
                new LatLng(39.030303, -84.464122),
                new LatLng(39.030305, -84.463936),
                new LatLng(39.030290, -84.463936),
                new LatLng(39.030293, -84.463477),
                new LatLng(39.029975, -84.463468),
                new LatLng(39.029975, -84.463945),
                new LatLng(39.029993, -84.463940),
                new LatLng(39.029989, -84.464099),
                new LatLng(39.029922, -84.464100),
                new LatLng(39.029920, -84.464313),
                new LatLng(39.029990, -84.464316)).strokeColor(Color.BLACK).fillColor(0x7F636363);


    private PolygonOptions kentonGarage = new PolygonOptions()
            .add(new LatLng(39.030651, -84.467264),
                new LatLng(39.029667, -84.467259),
                new LatLng(39.029653, -84.467267),
                new LatLng(39.029639, -84.467290),
                new LatLng(39.029650, -84.467320),
                new LatLng(39.029665, -84.467334),
                new LatLng(39.029685, -84.467335),
                new LatLng(39.029794, -84.467705),
                new LatLng(39.029838, -84.467701),
                new LatLng(39.029839, -84.467744),
                new LatLng(39.029789, -84.467746),
                new LatLng(39.029912, -84.468171),
                new LatLng(39.030697, -84.468169),
                new LatLng(39.030708, -84.468192),
                new LatLng(39.030781, -84.468193),
                new LatLng(39.030648, -84.467745),
                new LatLng(39.030604, -84.467743),
                new LatLng(39.030601, -84.467700),
                new LatLng(39.030650, -84.467698),
                new LatLng(39.030647, -84.467341),
                new LatLng(39.030668, -84.467329),
                new LatLng(39.030678, -84.467303),
                new LatLng(39.030667, -84.467273)).strokeColor(Color.BLACK).fillColor(0x7Fffa500);

    private PolygonOptions studentParkingLotM = new PolygonOptions()
            .add(new LatLng(39.032897, -84.467141),
                new LatLng(39.033683, -84.466151),
                new LatLng(39.034137, -84.466618),
                new LatLng(39.034178, -84.466765),
                new LatLng(39.034274, -84.466873),
                new LatLng(39.034234, -84.466951),
                new LatLng(39.033686, -84.467678),
                new LatLng(39.033649, -84.467640),
                new LatLng(39.033587, -84.467656),
                new LatLng(39.033322, -84.467568),
                new LatLng(39.032956, -84.467340)).strokeColor(Color.BLACK).fillColor(0x7Fffa500);

    private PolygonOptions facultyParkingLotD = new PolygonOptions()
            .add(new LatLng(39.032701, -84.461397),
                new LatLng(39.032506, -84.461394),
                new LatLng(39.032358, -84.461371),
                new LatLng(39.032302, -84.461100),
                new LatLng(39.031794, -84.461075),
                new LatLng(39.031789, -84.461346),
                new LatLng(39.031616, -84.461430),
                new LatLng(39.031653, -84.461763),
                new LatLng(39.031819, -84.462258),
                new LatLng(39.032342, -84.462292),
                new LatLng(39.032501, -84.462183),
                new LatLng(39.032512, -84.461809),
                new LatLng(39.032672, -84.461817)).strokeColor(Color.BLACK).fillColor(0x7Fffa500);

    private PolygonOptions facultyParkingLotV = new PolygonOptions()
            .add(new LatLng(39.028991, -84.463699),
                new LatLng(39.028615, -84.463687),
                new LatLng(39.028512, -84.463753),
                new LatLng(39.028331, -84.463762),
                new LatLng(39.028323, -84.463894),
                new LatLng(39.028973, -84.463914)).strokeColor(Color.BLACK).fillColor(0x7Fffa500);

    private PolygonOptions studentParkingLotI = new PolygonOptions()
            .add(new LatLng(39.033839, -84.46375),
                new LatLng(39.034043, -84.462909),
                new LatLng(39.033492, -84.462903),
                new LatLng(39.033425, -84.462886),
                new LatLng(39.033394, -84.463158),
                new LatLng(39.033167, -84.463158),
                new LatLng(39.033147, -84.463094),
                new LatLng(39.032875, -84.463061),
                new LatLng(39.032847, -84.463153),
                new LatLng(39.032781, -84.463178),
                new LatLng(39.032803, -84.463381),
                new LatLng(39.032878, -84.463725)).strokeColor(Color.BLACK).fillColor(0x7Fffa500);


    public void loadCoordinates(GoogleMap map)
    {
        map.addPolygon(griffinHall);
        map.addPolygon(scienceBuilding);
        map.addPolygon(landrumHall);
        map.addPolygon(fineArts);
        map.addPolygon(albrightHealthCenter);
        map.addPolygon(baptistStudentUnion);
        map.addPolygon(mathEducationPsychologyCenter);
        map.addPolygon(businessAcademicCenter);
        map.addPolygon(lucasAdministrativeCenter);
        map.addPolygon(foundersHall);
        map.addPolygon(nunnHall);
        map.addPolygon(regentsHall);
        map.addPolygon(studentUnion);
        map.addPolygon(steelyLibrary);
        map.addPolygon(universityCenter);
        map.addPolygon(kentonGarage);
        map.addPolygon(studentParkingLotM);
        map.addPolygon(facultyParkingLotD);
        map.addPolygon(facultyParkingLotV);
        map.addPolygon(studentParkingLotI);
    }

    public void insertMarkers (GoogleMap map)
    {
        map.addMarker(new MarkerOptions().position(new LatLng(39.030969, -84.466807)).title("Griffin Hall").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.030194, -84.465240)).title("Student Union").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.032428, -84.466249)).title("Science Building").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.029202, -84.466399)).title("Albright Health Center").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.029340, -84.465122)).title("Regents Hall").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.033249, -84.464401)).title("Baptist Student Union").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.030963, -84.464851)).title("Nunn Hall").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.031749, -84.465122)).title("Founder's Hall").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.031932, -84.463835)).title("Steely Library").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.032542, -84.464618)).title("Landrum Hall").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.029688, -84.463459)).title("Lucas Administrative Center").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.030140, -84.462360)).title("Math, Education, Psychology Center").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.031115, -84.461619)).title("Business Academic Center").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.031244, -84.463695)).title("Fine Arts Center").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.030169, -84.463950)).title("University Center").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.030244, -84.467783)).title("Visitor Parking Lot 1").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.033594, -84.466871)).title("Student Parking Lot 1").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.033190, -84.463529)).title("Student Parking Lot 2").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.031957, -84.461786)).title("Faculty Parking Lot 1").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
        map.addMarker(new MarkerOptions().position(new LatLng(39.028773, -84.463701)).title("Faculty Parking Lot 2").icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)));
    }
}