package mod.hilal.saif.android_manifest;

import a.a.a.Nx;
import android.net.Uri;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import mod.hilal.saif.lib.FileUtil;

public class AndroidManifestInjector {
    public static void getP(Nx nx, String str) {
        new ArrayList();
        new ArrayList();
        try {
            String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(str).concat("/Injection/androidmanifest/attributes.json");
            if (FileUtil.isExistFile(concat)) {
                ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(concat), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                if (arrayList.size() > 0) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        String str2 = (String) ((HashMap) arrayList.get(i)).get("value");
                        if (((String) ((HashMap) arrayList.get(i)).get("name")).equals("_application_permissions")) {
                            Nx nx2 = new Nx("uses-permission");
                            nx2.b(str2);
                            nx.a(nx2);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public static void getAppAttrs(Nx nx, String str) {
        addToApp(nx, str);
    }

    public static boolean getActivityAttrs(Nx nx, String str, String str2) {
        try {
            String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(str).concat("/Injection/androidmanifest/attributes.json");
            new ArrayList();
            if (!FileUtil.isExistFile(concat)) {
                return false;
            }
            ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(concat), new TypeToken<ArrayList<HashMap<String, Object>>>() {
            }.getType());
            for (int i = 0; i < arrayList.size(); i++) {
                if (((String) ((HashMap) arrayList.get(i)).get("name")).equals(str2.substring(0, str2.indexOf(".java")))) {
                    addToAct(nx, str, str2);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isActivityThemeUsed(Nx nx, String str, String str2) {
        try {
            String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(str).concat("/Injection/androidmanifest/attributes.json");
            new ArrayList();
            if (!FileUtil.isExistFile(concat)) {
                return false;
            }
            ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(concat), new TypeToken<ArrayList<HashMap<String, Object>>>() {
            }.getType());
            for (int i = 0; i < arrayList.size(); i++) {
                if ((((String) ((HashMap) arrayList.get(i)).get("name")).equals(str2.substring(0, str2.indexOf(".java"))) || ((String) ((HashMap) arrayList.get(i)).get("name")).equals("_apply_for_all_activities")) && ((String) ((HashMap) arrayList.get(i)).get("value")).contains("android:theme")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isActivityOrientationUsed(Nx nx, String str, String str2) {
        try {
            String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(str).concat("/Injection/androidmanifest/attributes.json");
            new ArrayList();
            if (!FileUtil.isExistFile(concat)) {
                return false;
            }
            ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(concat), new TypeToken<ArrayList<HashMap<String, Object>>>() {
            }.getType());
            for (int i = 0; i < arrayList.size(); i++) {
                if ((((String) ((HashMap) arrayList.get(i)).get("name")).equals(str2.substring(0, str2.indexOf(".java"))) || ((String) ((HashMap) arrayList.get(i)).get("name")).equals("_apply_for_all_activities")) && ((String) ((HashMap) arrayList.get(i)).get("value")).contains("android:screenOrientation")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isActivityKeyboardUsed(Nx nx, String str, String str2) {
        try {
            String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(str).concat("/Injection/androidmanifest/attributes.json");
            new ArrayList();
            if (!FileUtil.isExistFile(concat)) {
                return false;
            }
            ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(concat), new TypeToken<ArrayList<HashMap<String, Object>>>() {
            }.getType());
            for (int i = 0; i < arrayList.size(); i++) {
                if ((((String) ((HashMap) arrayList.get(i)).get("name")).equals(str2.substring(0, str2.indexOf(".java"))) || ((String) ((HashMap) arrayList.get(i)).get("name")).equals("_apply_for_all_activities")) && ((String) ((HashMap) arrayList.get(i)).get("value")).contains("android:windowSoftInputMode")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getLauncherActivity(String str) {
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(str).concat("/Injection/androidmanifest/activity_launcher.txt");
        if (!FileUtil.isExistFile(concat)) {
            return "main";
        }
        String readFile = FileUtil.readFile(concat);
        if (readFile.contains(" ") || readFile.contains(".")) {
            return "main";
        }
        return readFile;
    }

    public static void setLauncherActivity(String str, String str2) {
        FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(str).concat("/Injection/androidmanifest/activity_launcher.txt"), str2);
    }

    public static String mHolder(String str, String str2) {
        new ArrayList();
        ArrayList arrayList = new ArrayList(Arrays.asList(str.split("\n")));
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(str2).concat("/Injection/androidmanifest/activities_components.json");
        new ArrayList();
        if (FileUtil.isExistFile(concat)) {
            ArrayList arrayList2 = (ArrayList) new Gson().fromJson(FileUtil.readFile(concat), new TypeToken<ArrayList<HashMap<String, Object>>>() {
            }.getType());
            for (int i = 0; i < arrayList2.size(); i++) {
                String str3 = (String) ((HashMap) arrayList2.get(i)).get("name");
                String str4 = (String) ((HashMap) arrayList2.get(i)).get("value");
                if (!str4.trim().equals("")) {
                    for (int i2 = 3; i2 < arrayList.size(); i2++) {
                        String str5 = (String) arrayList.get(i2);
                        String str6 = (String) arrayList.get(i2 - 1);
                        if (str5.contains("android:name=\"") && str5.contains(str3) && str6.contains("<activity")) {
                            int i3 = i2;
                            while (true) {
                                if (i3 >= arrayList.size()) {
                                    break;
                                }
                                String str7 = (String) arrayList.get(i3 - 1);
                                if (((String) arrayList.get(i3)).matches("^\t\t<[a-zA-Z_-]+[^>]")) {
                                    if (str7.contains("\"/>")) {
                                        if (!str4.trim().equals("")) {
                                            arrayList.set(i3 - 1, ((String) arrayList.get(i3 - 1)).replace("\"/>", "\">"));
                                            arrayList.set(i3 - 1, ((String) arrayList.get(i3 - 1)).concat("\r\n").concat((String) ((HashMap) arrayList2.get(i)).get("value")).concat("\r\n").concat("</activity>"));
                                            break;
                                        }
                                    } else if (!str4.trim().equals("")) {
                                        arrayList.set(i3 - 2, ((String) arrayList.get(i3 - 2)).concat("\r\n").concat((String) ((HashMap) arrayList2.get(i)).get("value")));
                                        break;
                                    }
                                }
                                i3++;
                            }
                        }
                    }
                }
            }
        }
        String concat2 = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(str2).concat("/Injection/androidmanifest/app_components.txt");
        if (FileUtil.isExistFile(concat2) && !FileUtil.readFile(concat2).trim().equals("")) {
            arrayList.set(arrayList.size() - 3, ((String) arrayList.get(arrayList.size() - 3)).concat("\r\n").concat(FileUtil.readFile(concat2)));
        }
        String str8 = "";
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            str8 = str8.concat("\n").concat((String) arrayList.get(i4));
        }
        return str8;
    }

    public static void addToApp(Nx nx, String str) {
        boolean z;
        try {
            String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(str).concat("/Injection/androidmanifest/attributes.json");
            new ArrayList();
            if (FileUtil.isExistFile(concat)) {
                ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(concat), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                int i = 0;
                boolean z2 = false;
                while (i < arrayList.size()) {
                    String str2 = (String) ((HashMap) arrayList.get(i)).get("value");
                    if (((String) ((HashMap) arrayList.get(i)).get("name")).equals("_application_attrs")) {
                        nx.b((String) ((HashMap) arrayList.get(i)).get("value"));
                        if (str2.contains("android:theme")) {
                            z = true;
                            i++;
                            z2 = z;
                        }
                    }
                    z = z2;
                    i++;
                    z2 = z;
                }
                if (!z2) {
                    nx.b("android:theme=\"@style/AppTheme\"");
                    return;
                }
                return;
            }
            nx.b("android:theme=\"@style/AppTheme\"");
        } catch (Exception e) {
        }
    }

    public static void addToAct(Nx nx, String str, String str2) {
        try {
            String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(str).concat("/Injection/androidmanifest/attributes.json");
            new ArrayList();
            if (FileUtil.isExistFile(concat)) {
                ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(concat), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                    /* class mod.hilal.saif.android_manifest.AndroidManifestInjector.AnonymousClass8 */
                }.getType());
                for (int i = 0; i < arrayList.size(); i++) {
                    if (((String) ((HashMap) arrayList.get(i)).get("name")).equals(str2.substring(0, str2.indexOf(".java"))) || ((String) ((HashMap) arrayList.get(i)).get("name")).equals("_apply_for_all_activities")) {
                        nx.b((String) ((HashMap) arrayList.get(i)).get("value"));
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public static void setbooleans(String str, String str2, String str3) {
    }

    public static String mHolderOld(String str, String str2, String str3) {
        String concat;
        String str4;
        String str5 = "";
        try {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            ArrayList arrayList5 = new ArrayList();
            FileUtil.listDir(FileUtil.getExternalStorageDir().concat("/.sketchware/HilalSaifMod/projects/").concat(str2).concat("/manifest/activity"), arrayList);
            for (int i = 0; i < arrayList.size(); i++) {
                if (((String) arrayList.get(i)).endsWith(".java.adv")) {
                    arrayList2.add((String) arrayList.get(i));
                }
            }
            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                arrayList3.add(Uri.parse((String) arrayList2.get(i2)).getLastPathSegment().replace(".java.adv", ""));
            }
            ArrayList arrayList6 = new ArrayList(Arrays.asList(str.split("\n")));
            int i3 = 0;
            String str6 = "";
            while (i3 < arrayList6.size()) {
                if (((String) arrayList6.get(i3)).contains("android:name=\".") && ((String) arrayList6.get(i3)).contains("Activity\"") && i3 >= 1 && ((String) arrayList6.get(i3 - 1)).contains("<activity")) {
                    str6 = ((String) arrayList6.get(i3)).replace("\t\t\tandroid:name=\".", "").replace("\"", "").replace("\r", "");
                }
                if (!((String) arrayList6.get(i3)).contains("<activity") || str6.equals("")) {
                    str4 = str6;
                } else {
                    arrayList4.add(Integer.valueOf(i3 - 1));
                    arrayList5.add(str6);
                    str4 = "";
                }
                i3++;
                str6 = str4;
            }
            for (int i4 = 0; i4 < arrayList3.size(); i4++) {
                String readFile = FileUtil.readFile((String) arrayList2.get(i4));
                int intValue = ((Integer) arrayList4.get(arrayList5.indexOf((String) arrayList3.get(i4)))).intValue();
                if (arrayList3.get(i4) == "MainActivity") {
                    arrayList6.set(intValue, readFile.concat("\n"));
                } else {
                    arrayList6.set(intValue, ((String) arrayList6.get(intValue)).replace("/", "").concat("\n").concat(readFile));
                }
            }
            String concat2 = FileUtil.getExternalStorageDir().concat("/.sketchware/HilalSaifMod/projects/").concat(str2).concat("/manifest/extra/extra.adv");
            int i5 = 0;
            while (true) {
                if (i5 >= arrayList6.size()) {
                    break;
                } else if (((String) arrayList6.get(i5)).contains("DebugActivity")) {
                    arrayList6.add(i5 + 2, FileUtil.readFile(concat2));
                    break;
                } else {
                    i5++;
                }
            }
            int i6 = 0;
            while (i6 < arrayList6.size()) {
                if (str5 == "") {
                    concat = (String) arrayList6.get(i6);
                } else {
                    concat = str5.concat("\n").concat((String) arrayList6.get(i6));
                }
                i6++;
                str5 = concat;
            }
            str5.replace("\r", "");
            String concat3 = FileUtil.getExternalStorageDir().concat("/.sketchware/HilalSaifMod/projects/").concat(str2).concat("/manifest/replace/replace");
            new ArrayList();
            if (FileUtil.isExistFile(concat3)) {
                ArrayList arrayList7 = (ArrayList) new Gson().fromJson(FileUtil.readFile(concat3), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                    /* class mod.hilal.saif.android_manifest.AndroidManifestInjector.AnonymousClass9 */
                }.getType());
                for (int i7 = 0; i7 < arrayList7.size(); i7++) {
                    str5.replace(((HashMap) arrayList7.get(i7)).get("from").toString(), ((HashMap) arrayList7.get(i7)).get("to").toString());
                }
            }
            return str5;
        } catch (Exception e) {
            FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/HilalSaifMod/SK Manager/AndroidManifest.log"), e.toString());
            return str;
        }
    }

    public static void g() {
    }
}
