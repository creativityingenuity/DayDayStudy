package cn.yt.amapdemo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 高德地图使用的一些例子
 */
public class MainActivity extends AppCompatActivity {
    /**
     * 地图控件
     */
    private MapView mMapView;
    /**
     * 定义AMap 地图对象的操作方法与接口
     */
    private AMap mAMap;
    /**
     * 地图内置UI及手势控制器
     */
    private UiSettings mAMapUiSettings;
    /**
     * 定位类
     */
    private AMapLocationClient mLocationClient;
    /**
     * 轨迹对象
     */
    private Polyline polyline;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        initMap();
    }

    private void initMap() {
        mAMap = mMapView.getMap();
        mAMapUiSettings = mAMap.getUiSettings();
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        // 每10秒定位一次
        mLocationOption.setInterval(10 * 1000);
        //Device_Sensors
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
        //定位缓存
        mLocationOption.setLocationCacheEnable(false);
        //设置定位相关操作
        mLocationClient.setLocationOption(mLocationOption);
        //设置了地图是否允许显示缩放按钮
        mAMapUiSettings.setZoomControlsEnabled(false);
        //设置倾斜手势是否可用
        mAMapUiSettings.setTiltGesturesEnabled(false);
        //setRotateGesturesEnabled
        mAMapUiSettings.setRotateGesturesEnabled(false);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //启动定位
        mLocationClient.startLocation();
        //设置地图状态的监听接口
        mAMap.setOnCameraChangeListener(mCameraChangeListener);
        //设置marker点击监听
        mAMap.setOnMarkerClickListener(mMarkerClickListener);
    }


    /**
     * 显示marker
     */
    private void showMarker() {
        int viewID = 0;
        View mapMarker;
        //设置图片viewID = R.drawable.disk1;
        //设置view
        mapMarker = LayoutInflater.from(this).inflate(R.layout.marker_lp, null);
        BitmapDescriptor mBdAirShow = BitmapDescriptorFactory.fromView(mapMarker);
        MarkerOptions optionDisk = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(viewID))
                .position(latLng)
                .anchor(0.5f, 0.5f)
                .draggable(false);
        MarkerOptions optionPoint = new MarkerOptions().icon(mBdAirShow)
                .position(latLng)
                .anchor(0.5f, 0.5f)
                .draggable(false);
        Marker markerDisk = mAMap.addMarker(optionDisk);
        Marker markerPoint = mAMap.addMarker(optionPoint);
        markerDisk.setIcon(BitmapDescriptorFactory.fromResource(viewID));
        markerPoint.setIcon(BitmapDescriptorFactory.fromView(mapMarker));
        //设置marker旋转角度  markerPoint.setRotateAngle(bearing);
        markerDisk.setPosition(latLng);
        //marker删除 markerDisk.remove();
    }

    /**
     * 隐藏marker
     */
    private void hindMarker() {
        //markerDisk.setVisible(true);
        // markerPoint.setIcon(mBdAirShow); 更改图标
    }

    /**
     * 显示轨迹
     */
    private void showRouteline() {
        //轨迹
        List<LatLng> latLngs = new ArrayList<>();
        PolylineOptions options = new PolylineOptions();
        options.width(1.5f);
        options.color(Color.argb(255, 138, 43, 226));
        options.addAll(latLngs);
        polyline = mAMap.addPolyline(options);
        //        设置marker位置
        //        Marker marker = allPlaneMarkerList.get(allAircraftIds.indexOf(clickedPlaneId));
        //        LatLng latLng = latLngs.get(latLngs.size() - 1);
        //        marker.setPosition(latLng);
        //        marker.setAnchor(0.5f, 0.6f);
    }

    /**
     * 移除轨迹
     */
    private void hidePlaneLine() {
        if (polyline != null) {
            polyline.setVisible(false);
            polyline.remove();
            polyline = null;
        }
    }

    /**
     * 定位监听回调
     */
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                    //移动到定位点位置aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    //地图旋转aMap.moveCamera(CameraUpdateFactory.changeBearing(bearing));
                }
            }
        }
    };
    /**
     * 设置地图状态的监听接口
     */
    private AMap.OnCameraChangeListener mCameraChangeListener = new AMap.OnCameraChangeListener() {
        @Override
        public void onCameraChange(CameraPosition cameraPosition) {

        }

        @Override
        public void onCameraChangeFinish(CameraPosition cameraPosition) {

        }
    };

    /**
     * marker点击监听
     */
    private AMap.OnMarkerClickListener mMarkerClickListener = new AMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            return false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清理地图
        mAMap.clear();
        mMapView.onDestroy();
        // mapDraw.clear();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }

    /**
     * @Title: mapCoordinateConvert
     * @Description: gps坐标转高德坐标
     */
    public static LatLng mapCoordinateConvert(LatLng latLng, Context context) {
        CoordinateConverter converter = new CoordinateConverter(context);
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(latLng);
        LatLng lng = converter.convert();
        return lng;

    }

    //绘制部分内容
    /* private void drawAireaDistanceRing(DrawData drawData) {
        mapDraw.initColor(drawData.getDrawColor(), drawData.getDrawTrans(), DrawType.DISTANCE_RING);
        printLog("距离环：开始绘制");
        DistanceRingAirea distanceRingAirea = mapDraw.addDistanceRingAirrea(drawData);
        // 根据当前的放大比例进行文字控制
        if (tempZoomScale <= 8.0) {
            distanceRingAirea.updateTextState(false);
        } else {
            distanceRingAirea.updateTextState(true);
        }

        shapMap.put(drawData.getDrawDataId() + "", distanceRingAirea);

        printLog("距离环：结束绘制");

    }

    private void drawAireaPolygon(DrawData drawData) {
        mapDraw.initColor(drawData.getDrawColor(), drawData.getDrawTrans(), DrawType.POLYGON);
        printLog("多边形区域：开始绘制");
        List<LatLng> latlngs = new ArrayList<LatLng>();

        double txtLatMax = Double.parseDouble(drawData.getDrawLalons().get(0)
                .getDrawLatitude());
        double txtLngMax = Double.parseDouble(drawData.getDrawLalons().get(0)
                .getDrawLongitude());
        double txtLatMin = Double.parseDouble(drawData.getDrawLalons().get(0)
                .getDrawLatitude());
        double txtLngMin = Double.parseDouble(drawData.getDrawLalons().get(0)
                .getDrawLongitude());

        for (DrawLalon drawLalon : drawData.getDrawLalons()) {
            double lng = Double.parseDouble(drawLalon.getDrawLongitude());
            if (lng > txtLngMax)
                txtLngMax = lng;
            if (lng < txtLngMin)
                txtLngMin = lng;
            double lat = Double.parseDouble(drawLalon.getDrawLatitude());
            if (lat > txtLatMax)
                txtLatMax = lat;
            if (lat < txtLatMin)
                txtLatMin = lat;
            LatLng latlng = new LatLng(lat, lng);
            latlngs.add(latlng);
        }

        // 绘制多边形
        Polygon polygon = mapDraw.addPolygon(latlngs);
        Marker marker = aMap
                .addMarker(new MarkerOptions()
                        .position(
                                new LatLng((txtLatMax + txtLatMin) / 2,
                                        (txtLngMax + txtLngMin) / 2))
                        .icon(BitmapDescriptorFactory
                                .fromBitmap(convertXmlToBitmap(drawData
                                        .getDrawName()))).anchor(0.5f, 0.5f));

        PolygonAirea polygonAirea = new PolygonAirea(polygon, marker);
        // 根据当前缩放级别显示文字
        if (tempZoomScale <= 8.0) {
            polygonAirea.updateTextState(false);
        } else {
            polygonAirea.updateTextState(true);
        }
        shapMap.put(drawData.getDrawDataId() + "", polygonAirea);
        printLog("多边形区域：结束绘制");

    }

    private void drawAireaCircle(DrawData drawData) {
        mapDraw.initColor(drawData.getDrawColor(), drawData.getDrawTrans(), DrawType.CIRCLE);
        printLog("圆形区域：开始绘制");
        double lng = Double.parseDouble(drawData.getDrawLalons().get(0)
                .getDrawLongitude());
        double lat = Double.parseDouble(drawData.getDrawLalons().get(0)
                .getDrawLatitude());
        LatLng latlng = new LatLng(lat, lng);
        double radius = Double.parseDouble(drawData.getDrawRadius()) * 1000;
        Circle circle = mapDraw.addCircle(latlng, (int) radius);
        Marker text = aMap
                .addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .icon(BitmapDescriptorFactory
                                .fromBitmap(convertXmlToBitmap(drawData
                                        .getDrawName()))).anchor(0.5f, 0.5f));
        CircleAirea circleAirea = new CircleAirea(circle, text);
        if (tempZoomScale <= 8.0) {
            circleAirea.updateTextState(false);
        } else {
            circleAirea.updateTextState(true);
        }
        shapMap.put(drawData.getDrawDataId() + "", circleAirea);
        printLog("圆形区域：结束绘制");

    }

    private void drawAireaPoint(DrawData drawData) {
        printLog("点：开始绘制");
        double lng = Double.parseDouble(drawData.getDrawLalons().get(0)
                .getDrawLongitude());
        double lat = Double.parseDouble(drawData.getDrawLalons().get(0)
                .getDrawLatitude());
        LatLng latlng = new LatLng(lat, lng);

        Marker marker = aMap
                .addMarker(new MarkerOptions()
                        .title(String.valueOf(drawData.getDrawDataId()))
                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                .decodeResource(getResources(),
                                        R.drawable.map_point_normal)))
                        .position(latlng));
        Marker text = aMap
                .addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .icon(BitmapDescriptorFactory
                                .fromBitmap(convertXmlToBitmap(drawData
                                        .getDrawName()))).anchor(0.5f, 0.5f));
        DotAirea dotAirea = new DotAirea(marker, text);
        if (tempZoomScale <= 8.0) {
            dotAirea.updateTextState(false);
            printLog("点：结束绘制：" + "隐藏文字");
        } else {
            dotAirea.updateTextState(true);
        }

        shapMap.put(drawData.getDrawDataId() + "", dotAirea);

        printLog("点：结束绘制：" + drawData.getDrawDataId());
    }*/
}
