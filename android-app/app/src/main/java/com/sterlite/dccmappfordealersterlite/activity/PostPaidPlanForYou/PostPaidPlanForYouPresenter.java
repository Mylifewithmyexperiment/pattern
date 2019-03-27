package com.sterlite.dccmappfordealersterlite.activity.PostPaidPlanForYou;

import android.content.Context;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.PPP4U.AverageUsageDetailsDTO;
import com.sterlite.dccmappfordealersterlite.model.PPP4U.PostPaidPlansForYouDTO;
import com.sterlite.dccmappfordealersterlite.model.Plan;

public class PostPaidPlanForYouPresenter<V extends PostPaidPlanForYouContract.View> extends BasePresenter<V> implements PostPaidPlanForYouContract.Presenter<V> {
    static ArrayList<Plan> plansouter;
    static int cnt = 0;
    static int size = 0;
    static String description;
    Context context;
    AverageUsageDetailsDTO averageUsageDetails;

    @Override
    public void init() {
        if (getView() == null)
            return;
        getView().setUpView(false);
        context= DCCMDealerSterlite.appContext;
        startApiCall();
        String subscriberIdentifier = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, null);
        DCCMDealerSterlite.getDataManager().getPostPaidPlansForYou(subscriberIdentifier, Constants.BASIC_POSTPAID_PLAN_TYPE, new ApiHelper.OnApiCallback<PostPaidPlansForYouDTO>() {
            @Override
            public void onSuccess(PostPaidPlansForYouDTO baseResponse) {
                if(getView()==null)
                    return;
                PostPaidPlanForYouPresenter.this.onSuccess();
                getView().loadDataToView(baseResponse.getAverageUsageDetails().getRechargeAmount(),baseResponse.getAverageUsageDetails().getDataMBUsage(),baseResponse.getAverageUsageDetails().getVoiceMinUsage(),baseResponse.getAverageUsageDetails().getDataAmazonprimeUsage(),baseResponse.getAverageUsageDetails().getDataNetflixUsage(),baseResponse.getAverageUsageDetails().getDataYoutubeUsage(), (ArrayList<Plan>) baseResponse.getProduct().getGeneratedPlans());
            }

            @Override
            public void onFailed(int code, String message) {
                if(getView()==null)return;
                PostPaidPlanForYouPresenter.this.onFaild(code,message);
            }
        });
    }

    /*private void getPlans(String plantype) {
        AppApiHelper2.getWebServices().getPlans(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, ""), plantype, new Callback<PostPaidPlansForYouDTO>() {
            @Override
            public void success(PostPaidPlansForYouDTO postPaidPlansForYouDTO, Response response) {
                plansouter = new ArrayList<Plan>();
                averageUsageDetails=postPaidPlansForYouDTO.getAverageUsageDetails();
                Log.e("PostPaidPlansForYouDTO", " " + postPaidPlansForYouDTO);
                getView().hideProgressBar();
                size = postPaidPlansForYouDTO.getProduct().getProducts().size();
                for (ProductWsDTO plan :
                        postPaidPlansForYouDTO.getProduct().getProducts()) {
                    getDescription(plan,postPaidPlansForYouDTO.getProduct());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getView() == null) return;
                getView().hideProgressBar();
                Log.e("userSignUpWsResponseDTO", " " + error);
                if (Constants.IS_DUMMY_MODE) {
                    getView().showProgressBar();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (getView() == null) return;
                            getView().loadDataToView("$300", "1GB", "500 Mins", DummyLists.getPlans());
                            getView().hideProgressBar();
                        }
                    }, 1000);
                }
            }
        });
    }

    public String getDescription(final ProductWsDTO plan, final ProductDTO productSearchPageWsDTO) {

        DCCMDealerSterlite.getDataManager().getProductbyCode(plan.getCode(), new ApiHelper.OnApiCallback<String>() {
            @Override
            public void onSuccess(String desc) {
                cnt++;
                if (getView() == null) return;
                if (desc != null) {
                    Log.e("getDescription", desc);
                    description = desc;


                    Log.e("Plan Code desc", desc + "");

                    if (desc != null) {
                        String[] arrOfStr = desc.split("#");


                        plansouter.add(getPlan(plan.getCode(), productSearchPageWsDTO.getCategoryCode(),
                                plan.getName(),
                                plan.getOneTimeProductPrice().getValue() + "",
                                getData(plan.getCode()),
                                "Rollover upto 200GB",
                                getFreeBenefits(plan.getCode()),
                                getPlansBenefits(plan.getCode()),
                                getAdditionalBenefits(plan.getCode()),
                                arrOfStr[0],
                                arrOfStr[1],
                                arrOfStr[2]));
                    } else
                        plansouter.add(getPlan(plan.getCode(), productSearchPageWsDTO.getCategoryCode(),
                                plan.getName(),
                                plan.getOneTimeProductPrice().getValue() + "",
                                getData(plan.getCode()),
                                "Rollover upto 200GB",
                                getFreeBenefits(plan.getCode()),
                                getPlansBenefits(plan.getCode()),
                                getAdditionalBenefits(plan.getCode()),
                                getCallRates(plan.getCode()),
                                getSMSRates(plan.getCode()),
                                getValiditys(plan.getCode())));


                }
                if (cnt == size) {
                    if (getView() == null) return;
                    getView().loadDataToView(averageUsageDetails.getDataAmazonprimeUsage(),averageUsageDetails.getDataMBUsage(),averageUsageDetails.getDataNetflixUsage(),Plan.sortArray(plansouter));
                    getView().hideProgressBar();
                }
            }

            public Plan getPlan(String id, String type, String title, String price, String data, String rollover, String freeBenefits, ArrayList<PlanBenefit> planBenefits, ArrayList<String> additionalBenefits, String callRates, String sms, String validity) {
                Plan plan = new Plan();
                if (id.equalsIgnoreCase(Constants.CODE_MODEM)) {
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
                    plan.setValidity(getValidity(validity));
                } else {
                    plan.setPlanId(id);
                    plan.setPlanType(type);
                    plan.setPlanTitle(title);
                    plan.setPricePerMonth(price);
                    plan.setDataInGB(data);
                    plan.setRolloverDataInGB(rollover);
                    plan.setFreeBenefitInRupee(freeBenefits);
                    plan.setArrBenefits(planBenefits);
                    plan.setArrAdditionalBenefits(additionalBenefits);
                    plan.setCallRate(getCallRate(callRates));
                    plan.setSms(getSMSRate(sms));
                    plan.setValidity(getValidity(validity));
                }

                return plan;
            }

            public Plan getPlan(String id, String type, String title, String price, String data, String rollover, String freeBenefits, ArrayList<PlanBenefit> planBenefits, ArrayList<String> additionalBenefits) {
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

            public String getData(String code) {

                String data = null;

                if (code.equalsIgnoreCase(Constants.CODE1)) {

                    data = context.getString(R.string.twelve_gb);

                } else if (code.equalsIgnoreCase(Constants.CODE2)) {

                    data = context.getString(R.string.twenty_three_gb);

                } else if (code.equalsIgnoreCase(Constants.CODE3)) {

                    data = context.getString(R.string.four_gb);

                } else if (code.equalsIgnoreCase(Constants.CODE4)) {

                    data = context.getString(R.string.two_gb);
                } else if (code.equalsIgnoreCase(Constants.CODE5)) {

                    data = context.getString(R.string.four_gb);
                } else if (code.equalsIgnoreCase(Constants.CODE_BB)) {

                    data = context.getString(R.string.three_gb);
                } else if (code.equalsIgnoreCase(Constants.CODE_MODEM)) {

                    data = context.getString(R.string.four_gb);
                } else if (code.equalsIgnoreCase(Constants.CODE_IPTV)) {

                    data = context.getString(R.string.two_gb);
                }

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
            }

            public String getFreeBenefits(String code) {

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

                } else if (code.equalsIgnoreCase(Constants.CODE_IPTV)) {

                    freeBenefits = "200";

                } else if (code.equalsIgnoreCase(Constants.CODE_BB)) {

                    freeBenefits = "300";

                } else if (code.equalsIgnoreCase(Constants.CODE_MODEM)) {

                    freeBenefits = "400";

                } else {

                    freeBenefits = "100";

                }
//
// else if (code.equalsIgnoreCase(Constants.CODE6)) {
//
//                        freeBenefits = "100";
//                    }
                return freeBenefits;
            }

            public ArrayList<PlanBenefit> getPlansBenefits(String code) {
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
                } else if (code.equalsIgnoreCase(Constants.CODE5)) {
                    arrBenefts.add(getPlanBenefit(Constants.DATA, "", context.getString(R.string.two_gb_3g_data)));
                } else if (code.equalsIgnoreCase(Constants.CODE_IPTV)) {
                    arrBenefts.add(getPlanBenefit(Constants.ROUTER, "", context.getString(R.string.hundred_mbps)));
                } else if (code.equalsIgnoreCase(Constants.CODE_BB)) {
                    arrBenefts.add(getPlanBenefit(Constants.ROUTER, "", context.getString(R.string.Fifty_mbps)));
                } else if (code.equalsIgnoreCase(Constants.CODE_MODEM)) {
                    arrBenefts.add(getPlanBenefit(Constants.ROUTER, "", context.getString(R.string.hundred_mbps)));
                } else {
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

            public PlanBenefit getPlanBenefit(String image, String title, String des) {
                PlanBenefit planBenefit = new PlanBenefit();
                planBenefit.setBenefit_Image(image);
                planBenefit.setTitle(title);
                planBenefit.setDescription(des);
                return planBenefit;
            }

            public ArrayList<String> getAdditionalBenefits() {
                ArrayList<String> arrAddBenefits = new ArrayList<>();
                if (Constants.APP.equalsIgnoreCase(Constants.MTN)) {
                    arrAddBenefits.add(context.getString(R.string.arrbenefits_download1) + Constants.MTN + context.getString(R.string.arrbenefits_download2));
                    arrAddBenefits.add(context.getString(R.string.arrbenefits_south_africa));

                } else {
                    arrAddBenefits.add(context.getString(R.string.arrbenefits_download1) + Constants.TELKOMSEL + context.getString(R.string.arrbenefits_download2));
                    arrAddBenefits.add(context.getString(R.string.arrbenefits_indonesia));

                }
                return arrAddBenefits;
            }

            public ArrayList<String> getAdditionalBenefits(String code) {
                ArrayList<String> arrAddBenefits = new ArrayList<>();
                if (Constants.APP.equalsIgnoreCase(Constants.MTN)) {
                    switch (code) {
                        case Constants.CODE_MODEM:
                            arrAddBenefits.add("IPTV having different type of contents like HDTV and future coming 3D TV");
                            arrAddBenefits.add("Bandwidth on Demand (User and or service configurable)");
                            arrAddBenefits.add("TV over IP Service (MPEG2).");
                            break;
                        default:
                            arrAddBenefits.add(context.getString(R.string.arrbenefits_download1) + Constants.MTN + context.getString(R.string.arrbenefits_download2));
                            arrAddBenefits.add(context.getString(R.string.arrbenefits_south_africa));
                            break;
                    }


                } else {
                    arrAddBenefits.add(context.getString(R.string.arrbenefits_download1) + Constants.TELKOMSEL + context.getString(R.string.arrbenefits_download2));
                    arrAddBenefits.add(context.getString(R.string.arrbenefits_indonesia));

                }
                return arrAddBenefits;
            }


            public String getValiditys(String code) {
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
                    default:
                        return "1";
//                        case Constants.CODE4:
//                            return "1";
//                        case Constants.CODE5:
//                            return "1";
                }
            }

            public String getSMSRates(String code) {
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
                        return "0.700";
//                        case Constants.CODE4:
//                        return "0.500";
//                        case Constants.CODE5:
//                        return "0.700";
                }
            }

            public String getCallRates(String code) {
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
                    default:
                        return "1.000";
//                        case Constants.CODE4:
//                            return "1.000";
//                        case Constants.CODE5:
//                            return "1.100";
                }
            }

            public String getSMSRate(String sms) {
                return context.getString(R.string.callrate_sms1) + sms + context.getString(R.string.callrate_sms2);
            }

            public String getCallRate(String callRates) {
                return context.getString(R.string.callrate_minute1) + callRates + context.getString(R.string.callrate_minute2);
            }

            public String getValidity(String validity) {
                return context.getString(R.string.plan_validity) + validity + context.getString(R.string.month);
            }

            @Override
            public void onFailed(int code, String message) {
                cnt++;
                if (getView() == null) return;

            }
        });

        return description;
    }*/
}
//DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, "")
//POST_PAID_PLAN_TYPE