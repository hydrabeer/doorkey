package Copy_Credentials;

import entity.VaultItem;

public class CopyCredentialsView {
   private static CopyCredentialsController controller;

   public CopyCredentialsView(CopyCredentialsController controller) {
       this.controller = controller;
   }
    public static void onCopyUsernameButtonClicked(){
        //Call to Controller
//        controller.copyUsernameClicked();
    }
    public static void onCopyPasswordButtonClicked(){
//        Call to Controller
//        controller.copyPasswordClicked();
    }
}
