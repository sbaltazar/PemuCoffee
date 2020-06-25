package com.sbaltazar.pemucoffee.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.sbaltazar.pemucoffee.BuildConfig;
import com.sbaltazar.pemucoffee.databinding.FragmentCoffeeMapBinding;

import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

public class CoffeeMapFragment extends Fragment implements OnMapReadyCallback {

    // Permission request code
    private static int REQUEST_LOCATION_PERMISSION = 1;
    // Flag for checking location permissions
    private boolean isLocationPermissionGranted;

    private Location lastKnownLocation;
    // Location API provided by Google Play Services
    private FusedLocationProviderClient fusedLocationProviderClient;
    // Places API Client
    private PlacesClient mPlacesClient;

    private GoogleMap mMap;

    // Default values for map config when location is not available
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;

    // UI/Android Framework references
    private Context mContext;
    private Activity mActivity;
    private FragmentCoffeeMapBinding mBinding;

    public CoffeeMapFragment() {
    }

    public static CoffeeMapFragment newInstance() {
        CoffeeMapFragment fragment = new CoffeeMapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = getActivity();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = FragmentCoffeeMapBinding.inflate(inflater, container, false);

        mBinding.mapView.onCreate(savedInstanceState);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Places.initialize(mContext, BuildConfig.PLACES_API_KEY);
        mPlacesClient = Places.createClient(mContext);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
        // Getting map to display
        mBinding.mapView.getMapAsync(this);
    }

    /**
     * Because we are using a MapView instead of MapFragment we need to control the view lifecycle
     * inside the fragment
     */
    @Override
    public void onStart() {
        super.onStart();
        mBinding.mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBinding.mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBinding.mapView.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding.mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mBinding.mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mBinding.mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Checking if the user has location permissions
        checkLocationPermission();

        // Update Google Maps UI
        updateMapUI();

        // Get device's location
        getDeviceLocation();
    }

    private void checkLocationPermission() {

        // Checking the necessary permissions
        int locationPermission = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (locationPermission == PackageManager.PERMISSION_GRANTED) {
            isLocationPermissionGranted = true;
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    private void updateMapUI() {
        if (mMap == null) return;

        try {
            if (isLocationPermissionGranted) {
                // Show the 'My Location' layer and the button to find the user's location
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                checkLocationPermission();
            }
        } catch (SecurityException e) {
            Timber.e(e, "Error on updating map UI");
        }
    }

    private void getDeviceLocation() {

        try {
            if (isLocationPermissionGranted) {

                Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();

                locationTask.addOnCompleteListener(mActivity, task -> {
                    if (task.isSuccessful()) {
                        lastKnownLocation = task.getResult();

                        if (lastKnownLocation != null) {

                            LatLng latLng = new LatLng(lastKnownLocation.getLatitude(),
                                    lastKnownLocation.getLongitude());

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                        } else {
                            Timber.d("Current location is null. Using default location");
                            Timber.e(task.getException(), "Task's exception");

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation,
                                    DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                        // At this moment a position (obtained by the API or default) can be used
                        // for showing the nearby places that sells coffee
                        showCoffeePlaces();
                    }
                });
            }
        } catch (SecurityException e) {
            Timber.e(e, "Error on updating map UI");
        }
    }

    private void showCoffeePlaces() {
        if (mMap == null) {
            return;
        }

        if (isLocationPermissionGranted) {

            // Getting the needed projection for request the places info
            List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME,
                    Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.TYPES);

            FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);

            // Getting the requestes places
            Task<FindCurrentPlaceResponse> findCurrentPlaceTask = mPlacesClient
                    .findCurrentPlace(request);

            findCurrentPlaceTask.addOnCompleteListener(response -> {
                if (response.isSuccessful() && response.getResult() != null) {

                    FindCurrentPlaceResponse likelyPlaces = response.getResult();

                    for (PlaceLikelihood placeLikelihood : likelyPlaces.getPlaceLikelihoods()) {

                        // Checking the types of the place (Supermarket, coffee shop, pharmacy, etc)
                        List<Place.Type> placeTypes = placeLikelihood.getPlace().getTypes();

                        // By now we are checking only places that are coffee shop or sells food
                        // (maybe selling coffee)
                        if (placeTypes != null && (placeTypes.contains(Place.Type.CAFE) ||
                                        placeTypes.contains(Place.Type.FOOD))) {
                            Timber.d("Place '%s' has likelihood: %f, type: %s",
                                    placeLikelihood.getPlace().getName(),
                                    placeLikelihood.getLikelihood(),
                                    placeLikelihood.getPlace().getTypes().toString());

                            if (placeLikelihood.getPlace().getLatLng() != null) {
                                LatLng coffeeLatLng = placeLikelihood.getPlace().getLatLng();
                                String title = placeLikelihood.getPlace().getName();

                                // Adding a mark to the map to highlight the place
                                mMap.addMarker(new MarkerOptions().title(title)
                                        .position(coffeeLatLng));
                            }
                        }
                    }
                } else {
                    Timber.e(response.getException(), "Error getting the nearby places");
                }
            });
        } else {
            checkLocationPermission();
        }
    }

    /**
     * Called when user is asked for permissions
     *
     * @param requestCode Request code permission. Location code is used by now
     * @param permissions Requested permissions
     * @param grantResults The grant result. Can be either GRANTED or DENIED
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        isLocationPermissionGranted = false;

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isLocationPermissionGranted = true;
            }
            return;
        }

        updateMapUI();
    }

}
