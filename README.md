# Low Juice Location

This is a module to retrieve rough locations (Longitude/Latitude) from Network. 
For this method, device GPS is not required. 

When you turn-on Location Service in your device, It is using more & more juice in the device.
Generally in Android devices we can use only one day, after full charge. In that case, with when we turn-on location service, 
it will reduce like a half a day or less than that.

If you are build a App that need rough locations, like only your app need to get the get current area,
of the user, then you don't need GPS. This will be a solution for that.


## What we are doing here?

In this module we grab the location through Network Cells. Using Android SDK TelephonyManager, we find the device network operator
  information & current network cell information. With these data, we use the support of [OpenCellID](http://opencellid.org/).
  
## Install

## How you use

After you install the dependency, You can use following code snippet to retrieve the location from Network

```java
    try {
        LowJuiceLocator.getInstance(context, new OnLocationChangeListener() {
            @Override
            public void onLocationChange(LocationDetail locationDetail) {
                // Do something from location data
            }
        }).refreshLocation();
    } catch (AirplaneModeException e) {
        // Handle the Airplane mode case
    } catch (UnknownNetworkTypeException e) {
        // Handle the Unknow network type case
    }
```

## What are these 'Network cells'?

In the globe we all covered from GSM network. Through that we are doing calls, sms, data & etc. If you have a mobile phone, then you also under this network. 

Basically from 3 points we can create an area then according to that case, if we think about 3 GSM towers, we can create an area of Network. Simply that area can be call as a Network cell. 

In the globe all of these cells are uniquely named & gives an unique Ids. You can get more information about this from [Cellular Network Wiki](https://en.wikipedia.org/wiki/Cellular_network).


## How we pick location from this 'Network Cell'?

If you visit to following [OpenCellID](http://opencellid.org/), you also can play with it. If you can provide the parameters they request, then you can get the location according to the parameters you provide.

### Following are the basic parameters, require to get the location.

Parameter | Description
--------- | -----------
MCC | Mobile Country Code
MNC | Mobile Network Code
LAC | Location Area Code
Cell ID | Network cell's unique Id

This **MCC** & **MNC** are given for your Network provider. You can get your network provider's those details from [Mobile Country Code Wiki](https://en.wikipedia.org/wiki/Mobile_country_code).
You can find all above data using TelephonyManager in Android.


**Happy Coding...!**