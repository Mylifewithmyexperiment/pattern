package com.sterlite.dccmappfordealersterlite.Utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.data.network.model.Pagging;
import com.sterlite.dccmappfordealersterlite.model.Address;
import com.sterlite.dccmappfordealersterlite.model.CartDetails;
import com.sterlite.dccmappfordealersterlite.model.CartItem;
import com.sterlite.dccmappfordealersterlite.model.Device;
import com.sterlite.dccmappfordealersterlite.model.Inventory;
import com.sterlite.dccmappfordealersterlite.model.InventoryFilter.InventoryFilter;
import com.sterlite.dccmappfordealersterlite.model.InventoryFilter.Lvl1Filter;
import com.sterlite.dccmappfordealersterlite.model.InventoryFilter.Lvl2Filter;
import com.sterlite.dccmappfordealersterlite.model.InventoryFilter.RadioPOJO;
import com.sterlite.dccmappfordealersterlite.model.KYCSuccess;
import com.sterlite.dccmappfordealersterlite.model.Language;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanFilter.Filter;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanFilter.FilterContainer;
import com.sterlite.dccmappfordealersterlite.model.MyOrdersListing.Order;
import com.sterlite.dccmappfordealersterlite.model.OrderDetails;
import com.sterlite.dccmappfordealersterlite.model.OrderStatus;
import com.sterlite.dccmappfordealersterlite.model.Plan;
import com.sterlite.dccmappfordealersterlite.model.PlanBenefit;
import com.sterlite.dccmappfordealersterlite.model.ReccomandedPlanPOJO;
import com.sterlite.dccmappfordealersterlite.model.Store;
import com.sterlite.dccmappfordealersterlite.model.Track;

public class DummyLists {

    public static CartDetails cartDetails = new CartDetails();

    /*public static ArrayList<HomeItem> getHomeItems() {
        ArrayList<HomeItem> arrHomeItemList = new ArrayList<>();
        arrHomeItemList.clear();
        HomeItem item = new HomeItem();
        arrHomeItemList.add(item);

        HomeItem item2 = new HomeItem();
        arrHomeItemList.add(item2);

        return arrHomeItemList;
    }*/

    public static ArrayList<Plan> getPlans() {
        ArrayList<Plan> arrPlan = new ArrayList<>();

        arrPlan.add(getPlan("0", "prepaid", "RED Entertainment", Constants.CURRANCY_SYMBOL+"399", "40GB", "Rollover upto 200GB", "1498", getPlansBenefits(), getAdditionalBenefits()));
        arrPlan.add(getPlan("0", "prepaid", "RED Entertainment+", Constants.CURRANCY_SYMBOL+"499", "75GB", "Rollover upto 200GB", "4498", getPlansBenefits(), getAdditionalBenefits()));
        arrPlan.add(getPlan("0", "prepaid", "RED International", Constants.CURRANCY_SYMBOL+"999", "100GB", "Rollover upto 200GB", "5498", getPlansBenefits(), getAdditionalBenefits()));
        arrPlan.add(getPlan("0", "prepaid", "RED International+", Constants.CURRANCY_SYMBOL+"1299", "125GB", "Rollover upto 200GB", "7498", getPlansBenefits(), getAdditionalBenefits()));
        arrPlan.add(getPlan("0", "prepaid", "RED International L", Constants.CURRANCY_SYMBOL+"1999", "200GB", "Rollover upto 200GB", "9498", getPlansBenefits(), getAdditionalBenefits()));
        arrPlan.add(getPlan("0", "prepaid", "RED Signature", Constants.CURRANCY_SYMBOL+"2999", "300GB", "Rollover upto 500GB", "10498", getPlansBenefits(), getAdditionalBenefits()));

        return arrPlan;
    }

    public static Plan getPlan(String id, String type, String title, String price, String data, String rollover, String freeBenefits, ArrayList<PlanBenefit> planBenefits, ArrayList<String> additionalBenefits) {
        Plan plan = new Plan();
        plan.setPlanId(id);
        plan.setPlanType(type);
        plan.setPlanTitle(title);
        plan.setPricePerMonth(price);
        plan.setDataInGB(data);
        plan.setRolloverDataInGB(rollover);
        plan.setFreeBenefitInRupee(freeBenefits);
        plan.setArrBenefits(planBenefits);
        plan.setArrAdditionalBenefits(additionalBenefits);
        return plan;
    }

    public static boolean change = false;

    public static ArrayList<PlanBenefit> getPlansBenefits() {
        ArrayList<PlanBenefit> arrBenefts = new ArrayList<>();
        if (change) {
            change = false;
            arrBenefts.add(getPlanBenefit("image", "Movie,Music & shopping", "Complimentry Mobile insurance for life-damage repair + antivirus + extended warranty + Free pick-up and drop"));
            arrBenefts.add(getPlanBenefit("image", "Vodafone Play", "Enjoy live TV, Movies and shows with free subscription for 12 months"));
            arrBenefts.add(getPlanBenefit("image", "Movie,Music & shopping", "Complimentry Mobile insurance for life-damage repair + antivirus + extended warranty + Free pick-up and drop"));
            arrBenefts.add(getPlanBenefit("image", "Vodafone Play", "Enjoy live TV, Movies and shows with free subscription for 12 months"));
        } else {
            change = true;
            arrBenefts.add(getPlanBenefit("image", "Vodafone Play", "Enjoy live TV, Movies and shows with free subscription for 12 months"));
            arrBenefts.add(getPlanBenefit("image", "Movie,Music & shopping", "Complimentry Mobile insurance for life-damage repair + antivirus + extended warranty + Free pick-up and drop"));
            arrBenefts.add(getPlanBenefit("image", "Vodafone Play", "Enjoy live TV, Movies and shows with free subscription for 12 months"));
            arrBenefts.add(getPlanBenefit("image", "Movie,Music & shopping", "Complimentry Mobile insurance for life-damage repair + antivirus + extended warranty + Free pick-up and drop"));
        }

        return arrBenefts;
    }

    public static ArrayList<String> getAdditionalBenefits() {
        ArrayList<String> arrAddBenefits = new ArrayList<>();
        arrAddBenefits.add("Download 'My Vodafone' app to avail 1GB free");
        arrAddBenefits.add("Free 100Local + National SMS on plans with rental 499 and above");
        arrAddBenefits.add("Free roaming anywhere in India");
        return arrAddBenefits;
    }


    public static CartDetails getCartDetailsWithNoItems() {
        cartDetails.setCartItemList(new ArrayList<CartItem>());
        cartDetails.setGrandTotal("");
        return cartDetails;
    }

    public static CartDetails getCartDetailsWithItems() {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem("http://ubd.etechservices.biz/media/catalog/product/a/p/apple-iphone-6s-64gb-rose-gold03.jpg", "Apple - iPhone 6s 64GB - Rose Gold", "all new iphone", "\u20B9 2399", "\u20B9 2399", 1, 5));
        cartItems.add(new CartItem("http://ubd.etechservices.biz/media/catalog/product/i/p/iphone-6s-sliver-64gb-01.jpg", "Apple - iPhone 6s 64GB - Space Grey", "iphone 10", "\u20B9 1500", "\u20B9 3000", 2, 5));
        cartDetails.setCartItemList(cartItems);
        cartDetails.setGrandTotal("\u20B9 5399");
        return cartDetails;
    }

    public static KYCSuccess getKYCSuccessObject(Context context) {
        KYCSuccess kycSuccess = new KYCSuccess();
        kycSuccess.setUID(context.getString(R.string.lbl_customer_uid_number));
        kycSuccess.setTitle(context.getString(R.string.lbl_customer_kyc_title));
        kycSuccess.setDescription(context.getString(R.string.lbl_customer_kuc_descrpition));
        return kycSuccess;
    }

    public static ArrayList<Inventory> getInventoryList() {
        ArrayList<Inventory> arrInventory = new ArrayList<>();
        arrInventory.add(getInventory("0", "premium", "Rp 5000", "1234567890", "20% discount applied", "price expire - 2 h 0 mins"));
        arrInventory.add(getInventory("1", "free", "Rp 0", "9999999999", "", ""));
        arrInventory.add(getInventory("2", "premium", "Rp 5000", "8888888888", "50% discount applied", "price expire - 1 h 59 mins"));
        arrInventory.add(getInventory("3", "free", "Rp 0", "7777777777", "", ""));
        arrInventory.add(getInventory("4", "free", "Rp 0", "8584442233", "", ""));
        arrInventory.add(getInventory("5", "free", "Rp 0", "9955443300", "", ""));
        arrInventory.add(getInventory("6", "premium", "Rp 5000", "7755441133", "70% discount applied", "price expire - 4 h 10 mins"));
        return arrInventory;
    }

    public static InventoryFilter getInventoryFilters() {
        InventoryFilter inventoryFilter = new InventoryFilter();
        ArrayList<String> numberSeries = new ArrayList<>();
        numberSeries.add("none");
        numberSeries.add("140");
        numberSeries.add("120");
       /* numberSeries.add("821");
        numberSeries.add("822");*/
//        numberSeries.add("003");
        String lvl1Title = "Select Type";
        ArrayList<Lvl1Filter> lvl1Filters = new ArrayList<>();
//        ArrayList<Lvl2Filter> lvl2Filters=new ArrayList<>();
        Lvl2Filter lvl2FilterForPremium = new Lvl2Filter();
        ArrayList<RadioPOJO> radioPOJOS = new ArrayList<>();
        RadioPOJO priceHighToLow = new RadioPOJO();
        priceHighToLow.setText("High to Low");
        radioPOJOS.add(priceHighToLow);
        RadioPOJO priceLowToHigh = new RadioPOJO();
        priceLowToHigh.setText("Low to High");
        radioPOJOS.add(priceLowToHigh);
        lvl2FilterForPremium.setTitle("Sort By Price");
        lvl2FilterForPremium.setRadioList(radioPOJOS);
        Lvl1Filter freecard = new Lvl1Filter();
        freecard.setText("Free");
        freecard.setChecked(false);
        freecard.setLvl2Filter(null);
        lvl1Filters.add(freecard);
        Lvl1Filter premium = new Lvl1Filter();
        premium.setText("Premium");
        premium.setChecked(false);
        premium.setLvl2Filter(lvl2FilterForPremium);
        inventoryFilter.setNumberSeries(numberSeries);
//        inventoryFilter.setLvl1Filters(lvl1Filters);
//        inventoryFilter.setLvl1Title(lvl1Title);
        return inventoryFilter;
    }

    private static Inventory getInventory(String id, String type, String price, String number, String disCount, String expire) {
        Inventory inventory = new Inventory();
        inventory.setId(id);
        inventory.setNumberType(type);
        inventory.setNumber(number);
        inventory.setDiscountText(disCount);
        inventory.setExpireDateText(expire);
        inventory.setPrice(price);
        return inventory;
    }

    public static ArrayList<Store> getStoreList() {
        ArrayList<Store> arrStore = new ArrayList<>();
        arrStore.add(getStore("0", "Vodafone Store", "A/1, 100 Feet Rd, Jodhpur Tekra, Ahmedabad, Gujarat 380015", 23.0124774, 72.5207151));
        arrStore.add(getStore("1", "Vodafone Store", "Althan Tenament, 3 , Chintan Park , Opp, VMS, Main Road, Althan, Surat, Gujarat 395017", 23.0350546, 72.4594103));
        arrStore.add(getStore("2", "Vodafone Store", "Swastik Co-op Housing Society, Opp. Vipul Dudhiya, 53A, Swastik Society Cross Rd, Navrangpura, Ahmedabad, Gujarat 380009", 23.0395903, 72.4929771));
        arrStore.add(getStore("3", "Vodafone Store", "Hospital, 101, Asthmangal Complex, Near Rajasthan, Shahibag, Ahmedabad, Gujarat 380004", 23.0541593, 72.5259217));
        arrStore.add(getStore("4", "Vodafone Store", "Vodafone Store, Shop No. 1 & 2, Medicon Arcade, Ground Floor, Near Galaxy Cinema, Naroda Nationa, Naroda, Ahmedabad, Gujarat 382330", 23.0224846, 72.5012596));
        arrStore.add(getStore("5", "Vodafone Store", "Rajeshwari Complex,Opp.Gayatri Mandir,Viramgam Road,Sanand,Ahmedabad, Sanand VMS, Gujarat 382110", 23.0600586, 72.4655916));
        arrStore.add(getStore("6", "Vodafone Store", "Opp Patel Furniture Near Nanda Hall, VMS, Kothariya Main Rd, Rajkot, Gujarat 360022", 23.0450106, 72.5000486));
        return arrStore;
    }

    public static Store getStore(String id, String name, String address, Double lat, Double lng) {
        Store store = new Store();
        store.setStoreID(id);
        store.setStoreName(name);
        store.setStoreAddress(address);
        store.setLat(lat);
        store.setLng(lng);
        return store;
    }

    public static ArrayList<String> getFilterList() {
        ArrayList<String> arrFilterList = new ArrayList<>();
        arrFilterList.add("All");
        arrFilterList.add("Free");
        arrFilterList.add("Premium");
        arrFilterList.add("Number Series");
        arrFilterList.add("All");
        arrFilterList.add("Free");
        arrFilterList.add("Premium");
        arrFilterList.add("Number Series");
        return arrFilterList;
    }

    public static ArrayList<String> getFilterListForDevice() {
        ArrayList<String> arrFilterList = new ArrayList<>();
        arrFilterList.add("Prepaid");
        arrFilterList.add("Postpaid");
        return arrFilterList;
    }

    public static ArrayList<Device> getDevices() {
        ArrayList<Device> arrDevice = new ArrayList<>();
        arrDevice.add(getDevice("1", "Samsung On7 Pro", "9,490", "7,990", "1,500", true, getFeatures(), getTechSpecification()));
        arrDevice.add(getDevice("2", "Meizu m3 note", "6,969", "6,969", "0", true, getFeatures(), getTechSpecification()));
        arrDevice.add(getDevice("3", "Moto G Plus, 4th Gen", "15,999", "14,249", "1,750", true, getFeatures(), getTechSpecification()));
        arrDevice.add(getDevice("4", "Lenovo Vibe K4 Note", "16,565", "14,194", "2,371", false, getFeatures(), getTechSpecification()));
        arrDevice.add(getDevice("5", "Samsung On7 Pro", "9,490", "7,990", "1,500", true, getFeatures(), getTechSpecification()));

        return arrDevice;
    }

    public static ArrayList<String> getCitys() {
        ArrayList<String> arrCity = new ArrayList<>();
        arrCity.add("Ahmedabad");
        arrCity.add("Surat");
        arrCity.add("Baroda");
        arrCity.add("Mehsana");
        arrCity.add("Rajkot");

        return arrCity;
    }


    public static ArrayList<String> getFeatures() {
        ArrayList<String> arrFeatures = new ArrayList<>();
        arrFeatures.add("13MP primary camera with auto mode, beauty face, continuous shot, interval shot, panorama mode and 5MP front facing camera with palm gesture selfie and 120 degree selfie mode");
        arrFeatures.add("13.97 centimeters (5.5-inch) TFT capacitive touchscreen with 720 x 1280 pixels HD display");
        arrFeatures.add("Andorid v6.0 Marshmallow operating system with 1.2GHz Qualcomm Snapdragon quad core processor, 2GB RAM, 16GB internal memory expandable up to 128GB and dual SIM (micro+micro) dual-standby (4G+4G)");
        arrFeatures.add("3000mAH lithium-ion battery");
        arrFeatures.add("1 year manufacturer warranty for device and 6 months manufacturer warranty for in-box accessories including batteries from the date of purchase");
        return arrFeatures;
    }

    public static ArrayList<String> getTechSpecification() {
        ArrayList<String> arrTechSpecification = new ArrayList<>();
        arrTechSpecification.add("OS: Android");
        arrTechSpecification.add("RAM: 2 GB");
        arrTechSpecification.add("Item Weight: 172 g");
        arrTechSpecification.add("Product Dimensions: 7.8 x 15.2 x 0.8 cm");
        arrTechSpecification.add("Item model number: G-600FY\n");
        arrTechSpecification.add("Wireless communication technologies: Bluetooth, WiFi Hotspot");
        arrTechSpecification.add("Connectivity technologies: GSM, (850/900/1800/1900 MHz), 4G LTE, (2600/2100/2300/1900/1800/850/900/800 MHz)");
        arrTechSpecification.add("Special features: Dual SIM, GPS, Music Player, Video Player, FM Radio, Accelerometer, Proximity sensor, E-mail");
        arrTechSpecification.add("Other camera features: 13MP Primary & 5MP front");
        arrTechSpecification.add("Form factor: Touchscreen Phone");
        arrTechSpecification.add("Weight: 170 Grams");
        arrTechSpecification.add("Colour: Gold");
        arrTechSpecification.add("Battery Power Rating: 3000");
        arrTechSpecification.add("Whats in the box: Handset, Travel Adaptor, USB Cable, Earphones and User Guide");
        return arrTechSpecification;
    }

    public static Device getDevice(String id, String name, String price, String priceAfterDisCount, String priceSaved, Boolean inStock, ArrayList<String> arrFeatures, ArrayList<String> arrTechSpecification) {
        Device device = new Device();
        device.setId(id);
        device.setName(name);
        device.setPrice(price);
        device.setPriceAfterDisCount(priceAfterDisCount);
        device.setPriceSaved(priceSaved);
        device.setInStock(inStock);
        device.setArrFeatures(arrFeatures);
        device.setArrTechSpecification(arrTechSpecification);
        return device;
    }

    public static Pagging<Order> getNoRecordsForOrder(int resPageNumber) {
        Pagging<Order> pagging = new Pagging<>();
        pagging.setPageNumber(1);
        pagging.setHasMore(false);
        return pagging;
    }

    public static Pagging<Order> getTenRecords(int resPageNumber, boolean resHasMoreItems, boolean needItems) {
        return getTenRecords(resPageNumber, resHasMoreItems, needItems, false);
    }

    public static Pagging<Order> getTenRecords(int resPageNumber, boolean resHasMoreItems, boolean needItems, boolean isActivationRecords) {
        Pagging<Order> pagging = new Pagging<>();
        pagging.setPageNumber(resPageNumber);
        ArrayList<Order> orders = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (needItems) {
            for (int i = 0; i < 20; i++)
                orders.add(new Order(String.valueOf(i), format.format(new Date()), "Device", "shipped", "\u20B9 1500", isActivationRecords, true));
        }
        pagging.addItemToList(orders);
        pagging.setHasMore(resHasMoreItems);
        return pagging;
    }

    public static ArrayList<Track> getTrackingDetail() {
        ArrayList<Track> mDataList = new ArrayList<>();
        mDataList.add(new Track("Order placed successfully", "2017-02-10 14:00", OrderStatus.COMPLETED));
        mDataList.add(new Track("Order confirmed by seller", "2017-02-10 14:30", OrderStatus.COMPLETED));
        mDataList.add(new Track("Order processing initiated", "2017-02-10 15:00", OrderStatus.COMPLETED));
        Track track = new Track("Order is being readied for dispatch", "", OrderStatus.ACTIVE);
        track.setmDescription("Packet dispatch with track number 100\nDriver Name: xyz, Number: 9876543210");
        mDataList.add(track);
        mDataList.add(new Track("Item is packed and will dispatch soon", "", OrderStatus.INACTIVE));
        mDataList.add(new Track("Item has been given to the courier", "", OrderStatus.INACTIVE));
        mDataList.add(new Track("Item has reached courier facility at New Delhi", "", OrderStatus.INACTIVE));
        mDataList.add(new Track("Courier is out to delivery your order", "", OrderStatus.INACTIVE));
        mDataList.add(new Track("Item successfully delivered", "", OrderStatus.INACTIVE));
        return mDataList;
    }


    public static OrderDetails getOrderDetails() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setDisplayOrderId("14520000414");
        orderDetails.setOrderDate("07-Dec-2017 12:00 PM");
        orderDetails.setOrderTotal(Constants.CURRANCY_SYMBOL+"5549.00");
        orderDetails.setOrderStatus("pending");
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem("http://ubd.etechservices.biz/media/catalog/product/a/p/apple-iphone-6s-64gb-rose-gold03.jpg", "Apple - iPhone 6s 64GB - Rose Gold", "all new iphone", "\u20B9 2399", "\u20B9 2399", 1, 5));
        cartItems.add(new CartItem("http://ubd.etechservices.biz/media/catalog/product/i/p/iphone-6s-sliver-64gb-01.jpg", "Apple - iPhone 6s 64GB - Space Grey", "iphone 10", "\u20B9 1500", "\u20B9 3000", 2, 5));
        orderDetails.setProductDetails(cartItems);
        orderDetails.setPaymentMethod("cash on delivery");
        orderDetails.setBillingAddressDetails(new Address("etech", "developer", "9876543210", "AddressLine1", "AddressLine2", "Abd", "Gujarat", "India", "3800015", true));
        orderDetails.setOrderBillTo("etech developer");
        orderDetails.setOrderShipTo("etech developer");
        orderDetails.setShippingAddressDetails(new Address("etech", "developer", "9876543210", "AddressLine1", "AddressLine2", "Abd", "Gujarat", "India", "3800015", true));
        orderDetails.setItemTotal(Constants.CURRANCY_SYMBOL+"5399.00");
        orderDetails.setShippingAmount(Constants.CURRANCY_SYMBOL+"100.00");
        orderDetails.setTotalBeforeTax(Constants.CURRANCY_SYMBOL+"5499.00");
        orderDetails.setTaxAmount(Constants.CURRANCY_SYMBOL+"50.00");
        orderDetails.setTrackable(true);
//        orderDetails.setTrackable(false);
        orderDetails.setShippingMethod("home delivery");
//        orderDetails.setShippingMethod("store pick up");
//        orderDetails.setStoreName("abc store");
//        orderDetails.setStoreAddressDetails(new Address("etech", "developer", "9876543210", "AddressLine1", "AddressLine2", "Abd", "Gujarat", "India", "3800015", true));
        return orderDetails;
    }

    public static OrderDetails getOrderDetailPreview() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setDisplayOrderId("14520000414");
        orderDetails.setOrderDate("07-Dec-2017 12:00 PM");
        orderDetails.setOrderTotal(Constants.CURRANCY_SYMBOL+"5549.00");
        orderDetails.setGrandTotal(Constants.CURRANCY_SYMBOL+"5549.00");
        orderDetails.setOrderStatus("pending");
        orderDetails.setPaymentMethod("cash on delivery");
        orderDetails.setBillingAddressDetails(new Address("etech", "developer", "9876543210", "AddressLine1", "AddressLine2", "Abd", "Gujarat", "India", "3800015", true));
        orderDetails.setOrderBillTo("etech developer");
        orderDetails.setOrderShipTo("etech developer");
        orderDetails.setShippingAddressDetails(new Address("etech", "developer", "9876543210", "AddressLine1", "AddressLine2", "Abd", "Gujarat", "India", "3800015", true));
        orderDetails.setItemTotal(Constants.CURRANCY_SYMBOL+"5399.00");
        orderDetails.setShippingAmount(Constants.CURRANCY_SYMBOL+"100.00");
        orderDetails.setTotalBeforeTax(Constants.CURRANCY_SYMBOL+"5499.00");
        orderDetails.setTaxAmount(Constants.CURRANCY_SYMBOL+"50.00");
        orderDetails.setTrackable(true);
//        orderDetails.setTrackable(false);
        orderDetails.setShippingMethod("home delivery");
//        orderDetails.setShippingMethod("store pick up");
//        orderDetails.setStoreName("abc store");
//        orderDetails.setStoreAddressDetails(new Address("etech", "developer", "9876543210", "AddressLine1", "AddressLine2", "Abd", "Gujarat", "India", "3800015", true));

        orderDetails.setPlanName("RED Basic");
        orderDetails.setPlanBenefits("Hello dsfkjhdsfkh sdjkfkjdh\n jsfhsdf sdhjfhdshf jskdhhsdg hjskhdhdsjg hsdjhdjg");
        orderDetails.setDeliveryAmount("FREE");
        orderDetails.setNumber("0123456789");
        orderDetails.setNumberAmount(Constants.CURRANCY_SYMBOL+"0");
        orderDetails.setSimStatus("Activation");
        orderDetails.setSimAmount("FREE");
        orderDetails.setDeliveryDate("07-Dec-2017 12:00 PM");
        orderDetails.setExtraPlanDetail("Free Benefits worth "+Constants.CURRANCY_SYMBOL+"499");
        orderDetails.setUserEmail("user@mail.com");
        orderDetails.setPlanAmount(Constants.CURRANCY_SYMBOL+"299/mon");
        orderDetails.setDeliveryType("Shipping Address");
        return orderDetails;
    }

    public static ArrayList<FilterContainer> getFilterContainers() {
        ArrayList<FilterContainer> filterContainers = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            FilterContainer filterContainer = new FilterContainer();
            filterContainer.setTitle("Main Menu");
            ArrayList<Filter> arrayList = new ArrayList<>();
            arrayList.add(new Filter("Sub Menu1", "(4)", false));
            arrayList.add(new Filter("Sub Menu2", "(2)", false));
            arrayList.add(new Filter("Sub Menu3", "(5)", false));
            filterContainer.setFilterList(arrayList);
            filterContainers.add(filterContainer);
        }
        return filterContainers;
    }

    public static ArrayList<ReccomandedPlanPOJO> getRecommandedPlanPOJOS() {
        ArrayList<ReccomandedPlanPOJO> reccomandedPlanPOJOS = new ArrayList<>();
        // for (int i = 0; i < 5; i++) {
        ReccomandedPlanPOJO reccomandedPlanPOJO = new ReccomandedPlanPOJO();
        reccomandedPlanPOJO.setCurrencyCode(Constants.CURRANCY_SYMBOL);
        reccomandedPlanPOJO.setAmount("10");
        reccomandedPlanPOJO.setSortDesc("UNLIMITED");
        reccomandedPlanPOJOS.add(reccomandedPlanPOJO);
        reccomandedPlanPOJO = new ReccomandedPlanPOJO();
        reccomandedPlanPOJO.setCurrencyCode(Constants.CURRANCY_SYMBOL);
        reccomandedPlanPOJO.setAmount("20");
        reccomandedPlanPOJO.setSortDesc("UNLIMITED");
        reccomandedPlanPOJOS.add(reccomandedPlanPOJO);
        reccomandedPlanPOJO = new ReccomandedPlanPOJO();
        reccomandedPlanPOJO.setCurrencyCode(Constants.CURRANCY_SYMBOL);
        reccomandedPlanPOJO.setAmount("30");
        reccomandedPlanPOJO.setSortDesc("UNLIMITED");
        reccomandedPlanPOJOS.add(reccomandedPlanPOJO);
        //  }
        return reccomandedPlanPOJOS;
    }


    public static HashMap<String,String> getRechargeSummery(String price) {
        HashMap<String,String> hm= new HashMap<>();
        if(price.equalsIgnoreCase("10")){
          //  hm.put(Constants.DATA,"1 GB");
            hm.put(Constants.MONETARY,"10");
        }
        else if(price.equalsIgnoreCase("20")){
            //hm.put(Constants.DATA,"2 GB");
            hm.put(Constants.MONETARY,"20");
        }else{
           // hm.put(Constants.DATA,"3 GB");
            hm.put(Constants.MONETARY,"30");
        }
        return hm;
        }


//    public static ArrayList<MakeMyPlan> getMakeMyPlanList() {
//        ArrayList<MakeMyPlan> plans = new ArrayList<>();
//        plans.add(getMakeMyPlan("0", "49", getMakeMyPlanItemsList()));
//        plans.add(getMakeMyPlan("1", "99", getMakeMyPlanItemsList()));
//        plans.add(getMakeMyPlan("2", "149", getMakeMyPlanItemsList()));
//        plans.add(getMakeMyPlan("3", "199", getMakeMyPlanItemsList()));
//        plans.add(getMakeMyPlan("4", "299", getMakeMyPlanItemsList()));
//        return plans;
//    }

//    public static MakeMyPlan getMakeMyPlan(String id, String price, ArrayList<MakeMyPlanItem> items) {
//        MakeMyPlan makeMyPlan = new MakeMyPlan();
//        makeMyPlan.setPlanId(id);
//        makeMyPlan.setPrice(price);
//        makeMyPlan.setArtItems(items);
//        return makeMyPlan;
//    }
//
//    public static ArrayList<MakeMyPlanItem> getMakeMyPlanItemsList() {
//        ArrayList<MakeMyPlanItem> makeMyPlanItemArrayList = new ArrayList<>();
//        makeMyPlanItemArrayList.add(getMakeMyPlanItem("0", "Internet", 3, "GB", R.drawable.ic_mmp_internet, 4));
//        makeMyPlanItemArrayList.add(getMakeMyPlanItem("1", "Call", 2000, "Minutes", R.drawable.ic_mmp_call));
//        makeMyPlanItemArrayList.add(getMakeMyPlanItem("2", "SMS", 2000, "", R.drawable.ic_mmp_sms));
//        makeMyPlanItemArrayList.add(getMakeMyPlanItem("3", "Roaming", 2400, "Minutes", R.drawable.ic_mmp_roaming, 4));
//        return makeMyPlanItemArrayList;
//    }

//    public static MakeMyPlanItem getMakeMyPlanItem(String id, String name, int value, String extraText, int imageId) {
//        return getMakeMyPlanItem(id, name, value, extraText, imageId, 5);
//    }
//
//    public static MakeMyPlanItem getMakeMyPlanItem(String id, String name, int value, String extraText, int imageId, int tickCount) {
//        MakeMyPlanItem makeMyPlanItem = new MakeMyPlanItem();
//        makeMyPlanItem.setMakeMyPlanItemId(id);
//        makeMyPlanItem.setItemName(name);
//        makeMyPlanItem.setItemValue(value);
//        makeMyPlanItem.setItemExtraInText(extraText);
//        makeMyPlanItem.setItemImageId(imageId);
//        makeMyPlanItem.setTickCount(tickCount);
//        return makeMyPlanItem;
//    }

    public static List<Language> getLanguages() {
        ArrayList<Language> languages = new ArrayList<>();
        languages.add(new Language("English","en",false));
        languages.add(new Language("Bahasa","in",false));
        return languages;
    }



    public static PlanBenefit getPlanBenefit(String image, String title, String des) {
        PlanBenefit planBenefit = new PlanBenefit();
        planBenefit.setBenefit_Image(image);
        planBenefit.setTitle(title);
        planBenefit.setDescription(des);
        return planBenefit;
    }

    public static Plan getPlan(String id, String type, String title, String price, String data, String rollover, String freeBenefits, ArrayList<PlanBenefit> planBenefits, ArrayList<String> additionalBenefits,String callRates,String sms,String validity,Context context) {
        Plan plan = new Plan();
        if(id.equalsIgnoreCase(Constants.CODE_MODEM)){
            plan.setPlanId(id);
            plan.setPlanType(type);
            plan.setPlanTitle(title);
            plan.setPricePerMonth(price);
            plan.setDataInGB(data);
            plan.setRolloverDataInGB(rollover);
            plan.setFreeBenefitInRupee(freeBenefits);
            plan.setArrBenefits(planBenefits);
            plan.setArrAdditionalBenefits(additionalBenefits);
            plan.setCallRate("Speed @100Mbps");
            plan.setSms("");
            plan.setValidity(getValidity(validity,context));
        }
        else {
            plan.setPlanId(id);
            plan.setPlanType(type);
            plan.setPlanTitle(title);
            plan.setPricePerMonth(price);
            plan.setDataInGB(data);
            plan.setRolloverDataInGB(rollover);
            plan.setFreeBenefitInRupee(freeBenefits);
            plan.setArrBenefits(planBenefits);
            plan.setArrAdditionalBenefits(additionalBenefits);
            plan.setCallRate(getCallRate(callRates,context));
            plan.setSms(getSMSRate(sms,context));
            plan.setValidity(getValidity(validity,context));
        }

        return plan;
    }


   /* public static String getData(String code,Context context) {

        String data = null;

        if (code.equalsIgnoreCase(Constants.CODE1)) {

            data = context.getString(R.string.two_gb);

        } else if (code.equalsIgnoreCase(Constants.CODE2)) {

            data = context.getString(R.string.four_gb);

        } else if (code.equalsIgnoreCase(Constants.CODE3)) {

            data = context.getString(R.string.eight_gb);

        } else if (code.equalsIgnoreCase(Constants.CODE4)) {

            data = context.getString(R.string.two_gb);
        }else if (code.equalsIgnoreCase(Constants.CODE5)) {

            data = context.getString(R.string.four_gb);
        }
        else if (code.equalsIgnoreCase(Constants.CODE6)) {

            data = context.getString(R.string.six_gb);
        }
        else if (code.equalsIgnoreCase(Constants.CODE_BB)) {

            data = context.getString(R.string.three_gb);
        }
        else if (code.equalsIgnoreCase(Constants.CODE_MODEM)) {

            data = context.getString(R.string.hundred_gb);
        }
        else if (code.equalsIgnoreCase(Constants.CODE_IPTV)) {

            data = context.getString(R.string.two_gb);
        }
        else
            data=context.getString(R.string.two_gb);
//                    else if (code.equalsIgnoreCase(Constants.CODE5)) {
//
//                        data = "23 GB";
//
//                    } else if (code.equalsIgnoreCase(Constants.CODE6)) {
//
//                        data = "4 GB";
//
//                    }
        return data;
    }*/

    public static String getData(String code, Context context) {

        String data = null;

        if (code.equalsIgnoreCase(Constants.CODE1)) {

            data = context.getString(R.string.twelve_gb);

        } else if (code.equalsIgnoreCase(Constants.CODE2)) {

            data = context.getString(R.string.twenty_gb_);

        } else if (code.equalsIgnoreCase(Constants.CODE3)) {

            data = context.getString(R.string.ten_gb);

        } else if (code.equalsIgnoreCase(Constants.CODE4)) {

            data = context.getString(R.string.two_gb);
        } else if (code.equalsIgnoreCase(Constants.CODE5)) {

            data = context.getString(R.string.four_gb);
        } else if (code.equalsIgnoreCase(Constants.CODE6)) {

            data = context.getString(R.string.two_gb);
        } else if (code.equalsIgnoreCase(Constants.CODE7)) {

            data = context.getString(R.string.twenty_gb);
        } else if (code.equalsIgnoreCase(Constants.CODE8)) {

            data = context.getString(R.string.fourteen_gb);
        } else if (code.equalsIgnoreCase(Constants.CODE9)) {

            data = context.getString(R.string.sixty_six_gb);
        } else if (code.equalsIgnoreCase(Constants.CODE10)) {

            data = context.getString(R.string.two_gb);
        } else if (code.equalsIgnoreCase(Constants.CODE11)) {

            data = context.getString(R.string.twenty_two_gb);
        } else if (code.equalsIgnoreCase(Constants.CODE12)) {

            data = context.getString(R.string.fourteen_gb);
        } else if (code.equalsIgnoreCase(Constants.CODE13)) {

            data = context.getString(R.string.sixty_six_gb);
        }


        else if (code.equalsIgnoreCase(Constants.CODE14)) {

            data = context.getString(R.string.Data_22GB);
        }
        else if (code.equalsIgnoreCase(Constants.CODE15)) {

            data = context.getString(R.string.Data_22GB);
        }
        else if (code.equalsIgnoreCase(Constants.CODE16)) {

            data = context.getString(R.string.Data_30GB);
        }else if (code.equalsIgnoreCase(Constants.CODE17)) {

            data = context.getString(R.string.Data_33GB);
        }
        else if (code.equalsIgnoreCase(Constants.CODE24)) {

            data = context.getString(R.string.Data_5GB);
        }
        else if (code.equalsIgnoreCase(Constants.CODE25)) {

            data = context.getString(R.string.Data_15GB);
        }
        else if (code.equalsIgnoreCase(Constants.CODE26)) {

            data = context.getString(R.string.Data_Unlimited);
        }


        else if (code.equalsIgnoreCase(Constants.CODE18)) {

            data = context.getString(R.string.Data_20GB);
        }
        else if (code.equalsIgnoreCase(Constants.CODE19)) {

            data = context.getString(R.string.Data_30GB);
        }
        else if (code.equalsIgnoreCase(Constants.CODE20)) {

            data = context.getString(R.string.Data_5GB);
        }

        else if (code.equalsIgnoreCase(Constants.CODE21)) {

            data = context.getString(R.string.Data_10GB);
        }
        else if (code.equalsIgnoreCase(Constants.CODE22)) {

            data = context.getString(R.string.Data_40GB);
        }
        else if (code.equalsIgnoreCase(Constants.CODE23)) {

            data = context.getString(R.string.Data_Unlimited);
        }










        else if (code.equalsIgnoreCase(Constants.CODE_BB)) {

            data = context.getString(R.string.Data_Unlimited);
        } else if (code.equalsIgnoreCase(Constants.CODE_MODEM)) {

            data = context.getString(R.string.Data_Unlimited);
        } else if (code.equalsIgnoreCase(Constants.CODE_IPTV)) {

            data = context.getString(R.string.Data_Unlimited);
        }
        else

            data = context.getString(R.string.two_gb);


        return data;
    }

    /*public static String getFreeBenefits(String code) {

        String freeBenefits = null;

        if (code.equalsIgnoreCase(Constants.CODE1)) {

            freeBenefits = "100";

        } else if (code.equalsIgnoreCase(Constants.CODE2)) {

            freeBenefits = "200";

        } else if (code.equalsIgnoreCase(Constants.CODE3)) {

            freeBenefits = "400";

        } else if (code.equalsIgnoreCase(Constants.CODE4)) {

            freeBenefits = "100";
        }
        else if (code.equalsIgnoreCase(Constants.CODE5)) {

            freeBenefits = "200";

        }
        else if (code.equalsIgnoreCase(Constants.CODE6)) {

            freeBenefits = "400";

        }
        else if (code.equalsIgnoreCase(Constants.CODE_IPTV)) {

            freeBenefits = "200";

        }

        else if (code.equalsIgnoreCase(Constants.CODE_BB)) {

            freeBenefits = "400";

        }
        else if (code.equalsIgnoreCase(Constants.CODE_MODEM)) {

            freeBenefits = "400";

        }
        else {

            freeBenefits = "100";

        }
//
// else if (code.equalsIgnoreCase(Constants.CODE6)) {
//
//                        freeBenefits = "100";
//                    }
        return freeBenefits;
    }*/


    public static String getFreeBenefits(String code) {

        String freeBenefits = null;

        if (code.equalsIgnoreCase(Constants.CODE1)) {

            freeBenefits = "500";

        } else if (code.equalsIgnoreCase(Constants.CODE2)) {

            freeBenefits = "1000";

        } else if (code.equalsIgnoreCase(Constants.CODE3)) {

            freeBenefits = "200";

        } else if (code.equalsIgnoreCase(Constants.CODE4)) {

            freeBenefits = "100";
        } else if (code.equalsIgnoreCase(Constants.CODE5)) {

            freeBenefits = "200";

        }else if (code.equalsIgnoreCase(Constants.CODE6)) {
            freeBenefits = "100";

        } else if (code.equalsIgnoreCase(Constants.CODE7)) {
            freeBenefits = "400";

        } else if (code.equalsIgnoreCase(Constants.CODE8)) {
            freeBenefits = "300";

        } else if (code.equalsIgnoreCase(Constants.CODE9)) {
            freeBenefits = "200";

        } else if (code.equalsIgnoreCase(Constants.CODE10)) {
            freeBenefits = "100";

        } else if (code.equalsIgnoreCase(Constants.CODE11)) {
            freeBenefits = "400";

        } else if (code.equalsIgnoreCase(Constants.CODE12)) {
            freeBenefits = "300";

        } else if (code.equalsIgnoreCase(Constants.CODE13)) {
            freeBenefits = "200";

        }  else if (code.equalsIgnoreCase(Constants.CODE_IPTV)) {

            freeBenefits = "200";

        } else if (code.equalsIgnoreCase(Constants.CODE_BB)) {

            freeBenefits = "300";

        } else if (code.equalsIgnoreCase(Constants.CODE_MODEM)) {

            freeBenefits = "400";

        } else {

            freeBenefits = "100";

        }

        return freeBenefits;
    }



  /*  public static ArrayList<PlanBenefit> getPlansBenefits(String code,Context context) {
        ArrayList<PlanBenefit> arrBenefts = new ArrayList<>();
        if (code.equalsIgnoreCase(Constants.CODE1)) {

            arrBenefts.add(getPlanBenefit(Constants.VIDEO, context.getString(R.string.video_max), context.getString(R.string.two_gb_video_max)));
        } else if (code.equalsIgnoreCase(Constants.CODE2)) {

            arrBenefts.add(getPlanBenefit(Constants.VIDEO, context.getString(R.string.video_max), context.getString(R.string.two_gb_video_max)));
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.fifty_min_voice)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));


        } else if (code.equalsIgnoreCase(Constants.CODE3)) {

            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.twenty_five_min_voice)));
        } else if (code.equalsIgnoreCase(Constants.CODE4)) {
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));
        }else if (code.equalsIgnoreCase(Constants.CODE5)) {
            arrBenefts.add(getPlanBenefit(Constants.DATA, "", context.getString(R.string.two_gb_3g_data)));
        }
        else if (code.equalsIgnoreCase(Constants.CODE6)) {
            arrBenefts.add(getPlanBenefit(Constants.VIDEO, context.getString(R.string.video_max), context.getString(R.string.two_gb_video_max)));
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.fifty_min_voice)));                }
        else if (code.equalsIgnoreCase(Constants.CODE_IPTV)) {
            arrBenefts.add(getPlanBenefit(Constants.ROUTER, "", context.getString(R.string.hundred_mbps)));
        }
        else if (code.equalsIgnoreCase(Constants.CODE_BB)) {
            arrBenefts.add(getPlanBenefit(Constants.ROUTER, "", context.getString(R.string.Fifty_mbps)));
        }
        else if (code.equalsIgnoreCase(Constants.CODE_MODEM)) {
            arrBenefts.add(getPlanBenefit(Constants.ROUTER, "", context.getString(R.string.hundred_mbps)));
        }
        else {
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));

        }
//                    else if (code.equalsIgnoreCase(Constants.CODE5)) {
//
//                        arrBenefts.add(getPlanBenefit("image", "Voice", "25  Minutes Voice"));
//                    } else if (code.equalsIgnoreCase(Constants.CODE6)) {
//                        arrBenefts.add(getPlanBenefit("image", "SMS", "50 SMS"));
//                    }
        return arrBenefts;
    }
*/

    public static ArrayList<PlanBenefit> getPlansBenefits(String code, Context context) {
        ArrayList<PlanBenefit> arrBenefts = new ArrayList<>();
        if (code.equalsIgnoreCase(Constants.CODE1)) {

            arrBenefts.add(getPlanBenefit(Constants.VIDEO, context.getString(R.string.video_max), context.getString(R.string.two_gb_video_max)));
        } else if (code.equalsIgnoreCase(Constants.CODE2)) {

            arrBenefts.add(getPlanBenefit(Constants.VIDEO, context.getString(R.string.video_max), context.getString(R.string.two_gb_video_max)));
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.fifty_min_voice)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));


        } else if (code.equalsIgnoreCase(Constants.CODE3)) {

            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.thirty_min_voice)));
        } else if (code.equalsIgnoreCase(Constants.CODE4)) {
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));
        } else if (code.equalsIgnoreCase(Constants.CODE5)) {
            arrBenefts.add(getPlanBenefit(Constants.DATA, "", context.getString(R.string.two_gb_3g_data)));
        }else if (code.equalsIgnoreCase(Constants.CODE6)) {
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));

        } else if (code.equalsIgnoreCase(Constants.CODE7)) {
            arrBenefts.add(getPlanBenefit(Constants.VIDEO, context.getString(R.string.video_max), context.getString(R.string.two_gb_video_max)));
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.fiftyfive_min_voice)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));

        } else if (code.equalsIgnoreCase(Constants.CODE8)) {
            arrBenefts.add(getPlanBenefit(Constants.VIDEO, context.getString(R.string.video_max), context.getString(R.string.two_gb_video_max)));

        } else if (code.equalsIgnoreCase(Constants.CODE9)) {
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.thirty_three_min_voice)));
            arrBenefts.add(getPlanBenefit(Constants.NETFLIX, context.getString(R.string.arrbenefits_netflix), context.getString(R.string.eleven_gb)));
            arrBenefts.add(getPlanBenefit(Constants.PRIME, context.getString(R.string.arrbenefits_prime), context.getString(R.string.thirty_gb)));
            arrBenefts.add(getPlanBenefit(Constants.YOUTUBE, context.getString(R.string.arrbenefits_youtube), context.getString(R.string.twenty_two_gb)));

        }  else if (code.equalsIgnoreCase(Constants.CODE10)) {
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));

        } else if (code.equalsIgnoreCase(Constants.CODE11)) {
            arrBenefts.add(getPlanBenefit(Constants.VIDEO, context.getString(R.string.video_max), context.getString(R.string.two_gb_video_max)));
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.fifty_min_voice)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));

        } else if (code.equalsIgnoreCase(Constants.CODE12)) {
            arrBenefts.add(getPlanBenefit(Constants.VIDEO, context.getString(R.string.video_max), context.getString(R.string.two_gb_video_max)));

        } else if (code.equalsIgnoreCase(Constants.CODE13)) {
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.thirty_min_voice)));

        }  else if (code.equalsIgnoreCase(Constants.CODE_IPTV)) {
            arrBenefts.add(getPlanBenefit(Constants.ROUTER, "", context.getString(R.string.hundred_mbps)));
        } else if (code.equalsIgnoreCase(Constants.CODE_BB)) {
            arrBenefts.add(getPlanBenefit(Constants.ROUTER, "", context.getString(R.string.Fifty_mbps)));
        } else if (code.equalsIgnoreCase(Constants.CODE_MODEM)) {
            arrBenefts.add(getPlanBenefit(Constants.ROUTER, "", context.getString(R.string.hundred_mbps)));
        }


        else if (code.equalsIgnoreCase(Constants.CODE14)) {
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.Min_5500)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.Sms_Unlimited)));
        //    arrBenefts.add(getPlanBenefit(Constants.DATA, context.getString(R.string.arrbenefits_data), context.getString(R.string.Data_22GB)));

        }
        else if (code.equalsIgnoreCase(Constants.CODE15)) {
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.Min_5500)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.Sms_Unlimited)));

            arrBenefts.add(getPlanBenefit(Constants.YOUTUBE, context.getString(R.string.arrbenefits_youtube), context.getString(R.string.Data_20GB)));
            arrBenefts.add(getPlanBenefit(Constants.NETFLIX, context.getString(R.string.arrbenefits_netflix), context.getString(R.string.Data_10GB)));
            arrBenefts.add(getPlanBenefit(Constants.PRIME, context.getString(R.string.arrbenefits_prime), context.getString(R.string.Data_30GB)));

        }
        else if (code.equalsIgnoreCase(Constants.CODE16)) {
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.Min_8000)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.Sms_Unlimited)));

            arrBenefts.add(getPlanBenefit(Constants.YOUTUBE, context.getString(R.string.arrbenefits_youtube), context.getString(R.string.Data_22GB)));
            arrBenefts.add(getPlanBenefit(Constants.NETFLIX, context.getString(R.string.arrbenefits_netflix), context.getString(R.string.Data_11GB)));
            arrBenefts.add(getPlanBenefit(Constants.PRIME, context.getString(R.string.arrbenefits_prime), context.getString(R.string.Data_33GB)));

        }
        else if (code.equalsIgnoreCase(Constants.CODE17)) {
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.Min_8000)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.Sms_Unlimited)));

            arrBenefts.add(getPlanBenefit(Constants.YOUTUBE, context.getString(R.string.arrbenefits_youtube), context.getString(R.string.Data_22GB)));
            arrBenefts.add(getPlanBenefit(Constants.NETFLIX, context.getString(R.string.arrbenefits_netflix), context.getString(R.string.eleven_gb)));
            arrBenefts.add(getPlanBenefit(Constants.PRIME, context.getString(R.string.arrbenefits_prime), context.getString(R.string.Data_33GB)));

        }

        else if (code.equalsIgnoreCase(Constants.CODE24)) {
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.Min_Unlimited)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.Sms_Unlimited)));
        //    arrBenefts.add(getPlanBenefit(Constants.DATA, context.getString(R.string.arrbenefits_data), context.getString(R.string.Data_5GB)));
        }
        else if (code.equalsIgnoreCase(Constants.CODE25)) {
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.Min_Unlimited)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.Sms_Unlimited)));
        //    arrBenefts.add(getPlanBenefit(Constants.DATA, context.getString(R.string.arrbenefits_data), context.getString(R.string.Data_15GB)));
        }
        else if (code.equalsIgnoreCase(Constants.CODE26)) {
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.Min_Unlimited)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.Sms_Unlimited)));
        //    arrBenefts.add(getPlanBenefit(Constants.DATA, context.getString(R.string.arrbenefits_data), context.getString(R.string.Data_Unlimited)));
            arrBenefts.add(getPlanBenefit(Constants.YOUTUBE, context.getString(R.string.arrbenefits_youtube), context.getString(R.string.free_data)));
            arrBenefts.add(getPlanBenefit(Constants.NETFLIX, context.getString(R.string.arrbenefits_netflix), context.getString(R.string.free_membership)));
            arrBenefts.add(getPlanBenefit(Constants.PRIME, context.getString(R.string.arrbenefits_prime), context.getString(R.string.free_membership)));

        }



      else if (code.equalsIgnoreCase(Constants.CODE18)) {
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.Min_5000)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.Sms_Unlimited)));
         //   arrBenefts.add(getPlanBenefit(Constants.DATA, context.getString(R.string.arrbenefits_data), context.getString(R.string.Data_20GB)));


        }
        else if (code.equalsIgnoreCase(Constants.CODE19)) {
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.Min_6000)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.Sms_Unlimited)));
      //      arrBenefts.add(getPlanBenefit(Constants.DATA, context.getString(R.string.arrbenefits_data), context.getString(R.string.Data_30GB)));
        }
        else if (code.equalsIgnoreCase(Constants.CODE20)) {
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.Min_2000)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.Sms_Unlimited)));
          //  arrBenefts.add(getPlanBenefit(Constants.DATA, context.getString(R.string.arrbenefits_data), context.getString(R.string.Data_5GB)));
        }
        else if (code.equalsIgnoreCase(Constants.CODE21)) {
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.Min_3000)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.Sms_Unlimited)));
         //   arrBenefts.add(getPlanBenefit(Constants.DATA, context.getString(R.string.arrbenefits_data), context.getString(R.string.Data_10GB)));
        }
        else if (code.equalsIgnoreCase(Constants.CODE22)) {
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.Min_8000)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.Sms_Unlimited)));
         //   arrBenefts.add(getPlanBenefit(Constants.DATA, context.getString(R.string.arrbenefits_data), context.getString(R.string.Data_40GB)));
        }
        else if (code.equalsIgnoreCase(Constants.CODE23)) {
            arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.Min_Unlimited)));
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.Sms_Unlimited)));
         //   arrBenefts.add(getPlanBenefit(Constants.DATA, context.getString(R.string.arrbenefits_data), context.getString(R.string.Data_Unlimited)));
        }




        else {
            arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));

        }

        return arrBenefts;
    }

    public static ArrayList<String> getAdditionalBenefits(Context context) {
        ArrayList<String> arrAddBenefits = new ArrayList<>();
        if(Constants.APP.equalsIgnoreCase(Constants.MTN)) {
            arrAddBenefits.add(context.getString(R.string.arrbenefits_download1)+Constants.MTN+context.getString(R.string.arrbenefits_download2));
            arrAddBenefits.add(context.getString(R.string.arrbenefits_south_africa));

        }
        else if(Constants.APP.equalsIgnoreCase(Constants.VODAFONE)) {
            arrAddBenefits.add(context.getString(R.string.arrbenefits_download1)+Constants.VODAFONE+context.getString(R.string.arrbenefits_download2));
            arrAddBenefits.add(context.getString(R.string.arrbenefits_uk));

        }
        else if(Constants.APP.equalsIgnoreCase(Constants.TELKOMSEL)) {
            arrAddBenefits.add(context.getString(R.string.arrbenefits_download1)+Constants.TELKOMSEL+context.getString(R.string.arrbenefits_download2));
            arrAddBenefits.add(context.getString(R.string.arrbenefits_indonesia));

        }
        else{
            arrAddBenefits.add(context.getString(R.string.arrbenefits_download1)+Constants.STERLITE+context.getString(R.string.arrbenefits_download2));
            arrAddBenefits.add(context.getString(R.string.arrbenefits_india));

        }
        return arrAddBenefits;
    }

    public static ArrayList<String> getAdditionalBenefits(String code,Context context) {
        ArrayList<String> arrAddBenefits = new ArrayList<>();
        if(Constants.APP.equalsIgnoreCase(Constants.MTN)) {
            switch (code){
                case Constants.CODE_MODEM:
                    arrAddBenefits.add("IPTV having different type of contents like HDTV and future coming 3D TV");
                    arrAddBenefits.add("Bandwidth on Demand (User and or service configurable)");
                    arrAddBenefits.add("TV over IP Service (MPEG2).");
                    break;
                default:
                    arrAddBenefits.add(context.getString(R.string.arrbenefits_download1)+Constants.MTN+context.getString(R.string.arrbenefits_download2));
                    arrAddBenefits.add(context.getString(R.string.arrbenefits_south_africa));
                    break;
            }


        }
        else if(Constants.APP.equalsIgnoreCase(Constants.VODAFONE)) {
            switch (code){
                case Constants.CODE_MODEM:
                    arrAddBenefits.add("IPTV having different type of contents like HDTV and future coming 3D TV");
                    arrAddBenefits.add("Bandwidth on Demand (User and or service configurable)");
                    arrAddBenefits.add("TV over IP Service (MPEG2).");
                    break;
                default:
                    arrAddBenefits.add(context.getString(R.string.arrbenefits_download1)+Constants.VODAFONE+context.getString(R.string.arrbenefits_download2));
                    arrAddBenefits.add(context.getString(R.string.arrbenefits_uk));
                    break;
            }


        }
        else if(Constants.APP.equalsIgnoreCase(Constants.TELKOMSEL)) {
            arrAddBenefits.add(context.getString(R.string.arrbenefits_download1)+Constants.TELKOMSEL+context.getString(R.string.arrbenefits_download2));
            arrAddBenefits.add(context.getString(R.string.arrbenefits_indonesia));
        }
        else{
            arrAddBenefits.add(context.getString(R.string.arrbenefits_download1)+Constants.STERLITE+context.getString(R.string.arrbenefits_download2));
            arrAddBenefits.add(context.getString(R.string.arrbenefits_india));

        }
        return arrAddBenefits;
    }


    public static String getValiditys(String code) {
        switch (code) {
            case Constants.CODE1:
                return "1";
            case Constants.CODE2:
                return "1";
            case Constants.CODE3:
                return "1";
            case Constants.CODE4:
                return "1";
            case Constants.CODE5:
                return "1";
            case Constants.CODE6:
                return "1";
            default:
                return "1";
//                        case Constants.CODE4:
//                            return "1";
//                        case Constants.CODE5:
//                            return "1";
        }
    }

    public static String getSMSRates(String code) {
        switch (code) {
            case Constants.CODE1:
                return "0.700";
            case Constants.CODE2:
                return "0.500";
            case Constants.CODE3:
                return "0.700";
            case Constants.CODE4:
                return "0.500";
            case Constants.CODE5:
                return "0.700";
            default:
                return "0.000";
//                        case Constants.CODE4:
//                        return "0.500";
//                        case Constants.CODE5:
//                        return "0.700";
        }
    }

    public static String getCallRates(String code) {
        switch (code) {
            case Constants.CODE1:
                return "1.100";
            case Constants.CODE2:
                return "1.000";
            case Constants.CODE3:
                return "1.100";
            case Constants.CODE4:
                return "1.000";
            case Constants.CODE5:
                return "1.100";

            case Constants.CODE23:
                return "0.000";
            case Constants.CODE24:
                return "0.000";
            case Constants.CODE25:
                return "0.000";
            case Constants.CODE26:
                return "0.000";
            default:
                return "1.000";
//                        case Constants.CODE4:
//                            return "1.000";
//                        case Constants.CODE5:
//                            return "1.100";
        }
    }
    public static String getSMSRate(String sms,Context context) {
        return context.getString(R.string.callrate_sms1)+sms+context.getString(R.string.callrate_sms2);
    }

    public static String getCallRate(String callRates,Context context) {
        return context.getString(R.string.callrate_minute1)+callRates+context.getString(R.string.callrate_minute2);
    }

    public static String getValidity(String validity,Context context) {
        return context.getString(R.string.plan_validity)+validity+context.getString(R.string.month);
    }
}
