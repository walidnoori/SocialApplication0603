package social.application.personalactivities;

public class Upload {
    private String mName;
    private String mImageUri;

    public Upload (){
      // empty constructor needed
    }


    public Upload (String name, String imageUri){
        if(name.trim().equals("")){
            name = "No Name";
        }

        mName = name;
        mImageUri = imageUri;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUri (){
        return mImageUri;
    }

    public void setImageUri (String imageUri){
        mImageUri = imageUri;
    }
}
