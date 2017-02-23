package in.ac.jntuace.noticeboard.tasks;

import android.content.Context;
import android.widget.Toast;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import in.ac.jntuace.noticeboard.BottomFragment;
import in.ac.jntuace.noticeboard.R;
import in.ac.jntuace.noticeboard.adapters.SetupBottomPagerAdapter;
import okhttp3.OkHttpClient;

public class SetupHelper {
    public Context context;
    String department = "";
    String position = "";

    public SetupHelper(Context context){
        this.context = context;
    }

    public void bottomPassOne(SetupBottomPagerAdapter adapter){
        BottomFragment fragment = adapter.getFragment(1);
        int selectedId = fragment.departmentSelect.getCheckedRadioButtonId();
        switch (selectedId){
    case R.id.engineering_civil:department="01";break;
    case R.id.engineering_eee:department="02";break;
    case R.id.engineering_mech:department="03";break;
    case R.id.engineering_ece:department="04";break;
    case R.id.engineering_cse:department="05";break;
    case R.id.engineering_chem:department="08";break;
    case R.id.non_maths:department="mm";break;
    case R.id.non_physics:department="pp";break;
    case R.id.non_chemistry:department="cc";break;
    case R.id.non_humanities:department="hh";break;
}
         }
    public String bottomPassTwo(SetupBottomPagerAdapter adapter){
        BottomFragment fragment = adapter.getFragment(2);
        int selectedId = fragment.positionSelect.getCheckedRadioButtonId();

        switch (selectedId){
            case R.id.position_hod:position="ho";break;
            case R.id.position_regular:position="re";break;
            case R.id.position_adhoc:position="ad";break;
            case R.id.position_non_teaching:position="nt";break;
                   }
               return position+"11SS"+department+"00";
    }
}
