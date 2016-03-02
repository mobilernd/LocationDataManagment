package appoverview.pro.starpaid.com.locationdatamanagment.Utils;

import android.content.Context;
import android.content.res.Resources;

public  class  GeneralServices
{

 public Context services_context ;

  public   GeneralServices(Context context)
    {

        services_context = context ;
    }


    public String get_title_name_from_TAG_NAME_PHOTO(String nameAndPhoto)
    {

        String[] photo_name_only = nameAndPhoto.split("@");

        return photo_name_only[0];



    }

    public String get_photo_name_from_TAG_NAME_PHOTO(String nameAndPhoto)
    {
        String[] photo_name_only = nameAndPhoto.split("@");
        return photo_name_only[1];
    }


    public    String get_image_from_drawable(String castle)
    {

        Resources res = services_context.getResources();
        String mDrawableName = castle;

        int resourceId = res.getIdentifier(mDrawableName , "drawable", services_context.getPackageName());
//            Drawable drawable = res.getDrawable(resourceId);
//            icon.setImageDrawable(drawable );
        return Integer.toString(resourceId);
    }



}
