package java.android.quanlybanhang.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.CongAdapter.Account_fragment;
import java.android.quanlybanhang.CongAdapter.Cart_Fragment;
import java.android.quanlybanhang.CongAdapter.HomeFragment;
import java.android.quanlybanhang.CongAdapter.MyNotification;
import java.android.quanlybanhang.CongAdapter.ProductAdapter;
import java.android.quanlybanhang.CongAdapter.Putnotification;
import java.android.quanlybanhang.CongAdapter.TableFragment;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.Sonclass.CuaHang;
import java.android.quanlybanhang.Sonclass.HomeProduct;
import java.android.quanlybanhang.Sonclass.KhachHang;
import java.android.quanlybanhang.Sonclass.SanPham;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class KhachHangActivity extends AppCompatActivity {


    public void setProducts(List<SanPham> products) {
        this.products = products;
    }
    private Context context =this;
    private  List<SanPham> products;
    public static KhachHang khachHang ;
    public    Fragment fragment;
    public static String idCartShop;
    private String Shop_Id;
    private int mCount;
    private View viewEndAnimation;
    private ImageView viewAnimation;
    private DatabaseReference mReference;
    public static    AHBottomNavigation bottomNavigation;
    public static Cart_Fragment cart_fragment;
    private int pos;



    public AHBottomNavigation getBottomNavigation() {
        return bottomNavigation;
    }

    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }

    public String getShop_Id() {
        return Shop_Id;
    }

    public List<SanPham> getProducts() {
        return products;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("BBB","onCreate Mainactivity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.khachhang_activity);
        mReference=FirebaseDatabase.getInstance().getReference();



        Intent intent =this.getIntent();
        Bundle bundle= intent.getExtras();

        if(bundle!=null)
        {
            if(bundle.getSerializable("khachhang")!=null)
            {
                khachHang = (KhachHang) bundle.getSerializable("khachhang");
            }
        }else {
            khachHang = new KhachHang("congbui",
                    "cb@gmail.com","20/03/1999","0374193095","idKhachHang");
        }


        setMenuItem();

       // setCountProductInBuild(products.size(),KhachHangActivity.this);

        //        mReference.child("gioHang").child(khachHang.getIdKhachhang()).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                for (DataSnapshot snapshot1: snapshot.getChildren())
//                {
//                    for (DataSnapshot snapshot2:snapshot1.getChildren())
//                    {
//
//
//                    }
//                }
//
//
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        Fragment fragment1=new HomeFragment(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment1).commit();
        pos=0;

        getSuperQuangCao();
    }

    public static  void setCountProductInBuild(int count,AppCompatActivity activity)
    {

        AHNotification notification = new AHNotification.Builder()
                .setText(String.valueOf(count))
                .setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.red))
                .setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.green))
                .build();
            bottomNavigation.setNotification(notification, 1);

    }



//    private List<LoaiTrai> getList()
//    {
//        List<LoaiTrai> loaiTrais=new ArrayList<>();
//
//        List<SanPham> trais=new ArrayList<>();
//
//        trais.add(new Trai(R.mipmap.bamdth,"anh Bui"));
//        trais.add(new Trai(R.mipmap.baxe,"anh Cong"));
//        trais.add(new Trai(R.mipmap.cauho,"ho dep"));
//        trais.add(new Trai(R.mipmap.chuald,"chua"));
//        trais.add(new Trai(R.mipmap.sauae,"6 ae"));
//        trais.add(new Trai(R.mipmap.child,"child"));
//
//        loaiTrais.add(new LoaiTrai("Dep trai 1",trais));
//        loaiTrais.add(new LoaiTrai("Dep trai 2",trais));
//        loaiTrais.add(new LoaiTrai("Dep trai 3",trais));
//        loaiTrais.add(new LoaiTrai("Dep trai 4",trais));
//        loaiTrais.add(new LoaiTrai("Dep trai 5",trais));
//
//
//        return loaiTrais;
//    }



//    private  List<SanphamChon> dsSanPhamDaChon()
//    {
//        List<SanphamChon> ds=new ArrayList<SanphamChon>();
//
//        SanPham sanPham=new SanPham();
//        SanphamChon sanphamChon=new SanphamChon();
//
//
//        sanPham=new SanPham(R.mipmap.bohuc,"Nước tăng lực Red Bull loong 240ml ",15000,10000,"Red Bull","bbb",1);
//        sanphamChon=new SanphamChon(sanPham,3);
//        ds.add(sanphamChon);
//
//
//        sanPham=new SanPham(R.mipmap.numberone,"Nước tăng lực Number 1 chai 330ml ",10000,8000,"Numbre One","bbb",1);
//        sanphamChon=new SanphamChon(sanPham,2);
//        ds.add(sanphamChon);
//
//        return ds;
//    }


    private void setMenuItem()
    {
         bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_baseline_home_24, R.color.red);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_4, R.drawable.ic_baseline_shopping_cart_24, R.color.blue);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_baseline_photo_library_24, R.color.blue);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_baseline_sentiment_satisfied_alt_24, R.color.yellow);
// Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        //doi mau cho background menu khi item duoc chon
        bottomNavigation.setColored(false);

        //doi mau cho icon khi duoc chon
        bottomNavigation.setAccentColor(getResources().getColor(R.color.pink));


        //mau cua icon ko dc chon
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.black));
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                Log.d("AAA",""+position);
               if(position==0)
               {
//
                   if(getSupportFragmentManager().findFragmentByTag("homefragment")!=null)
                   {
                       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,getSupportFragmentManager().findFragmentByTag("homefragment")).commit();
                   }else{
                       fragment=new HomeFragment(KhachHangActivity.this);

                       FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                       transaction.replace(R.id.fragment_container,fragment);
                       transaction.addToBackStack("cartfragment");
                       transaction.commit();
                   }

               }else  if(position==1)
               {

                        Cart_Fragment fragment1=new Cart_Fragment(getCartList(KhachHangActivity.this),idCartShop);
                        fragment1.setData(new ProductAdapter.SetPos() {
                            @Override
                            public void setPos(int size) {
                                setCountProductInBuild(size,KhachHangActivity.this);
                            }
                        });
                        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container,fragment1);
                        transaction.addToBackStack("homefragment");
                        transaction.commit();




               }else  if(position==2)
               {

                       fragment=new Account_fragment();
                    FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                   transaction.replace(R.id.fragment_container,fragment);
                   transaction.addToBackStack("accountfragment");
                   transaction.commit();
               }else {

                       fragment =new TableFragment();

                   FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                   transaction.replace(R.id.fragment_container,fragment);
                   transaction.addToBackStack("tablefragment");
                   transaction.commit();

               }

                return true;
            }
        });
    }

    public View getViewEndAnimation() {
        return viewEndAnimation;
    }

    public ImageView getViewAnimation() {
        return viewAnimation;
    }

    public List<SanPham> getListProduct()
    {

        List<SanPham> trais=new ArrayList<SanPham>();
        mReference.child("gioHang").child(khachHang.getIdKhachhang()).child("sanPham").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    String key= snapshot1.getKey();
                    SanPham sanPham=snapshot.child(key).getValue(SanPham.class);
                    boolean flag=true;
                    for (int i = 0; i < products.size(); i++) {
                        if (sanPham.getNameProduct().equals(products.get(i).getNameProduct()))
                        {
                            flag=false;
                            break;
                        }
                    }
                    if(flag)
                    {
                        trais.add(sanPham);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return trais;
    }

    public static List<SanPham> getCartList(AppCompatActivity activity)
    {

        List<SanPham> trais=new ArrayList<SanPham>();
        DatabaseReference mReference1=FirebaseDatabase.getInstance().getReference();
        mReference1.child("gioHang").child(khachHang.getIdKhachhang()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot snapshot1: snapshot.getChildren())
                {

                    if (snapshot1.getKey().equals("sanPham"))
                    {
                        for (DataSnapshot snapshot3:snapshot1.getChildren())
                        {
                            SanPham sanPham=snapshot3.getValue(SanPham.class);
                            sanPham.setSoluong(1);
                            trais.add(sanPham);
                            setCountProductInBuild(trais.size(),activity);
                        }
                    }else if(snapshot1.getKey().equals("idQuan"))
                    {
                        idCartShop =  snapshot1.getValue(String.class);

                    }
                    else {
                        Toast.makeText(activity.getApplicationContext(),"null",Toast.LENGTH_SHORT).show();
                    }
                }

                setCountProductInBuild(trais.size(),activity);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return trais;
    }

    public void addProduct()
    {
        mReference.child("cuaHang").push().setValue(new CuaHang("JxZOOK1RzcMM7pL5I6naGZfYSsu2","https://firebasestorage.googleapis.com/v0/b/quanlybanhang-26023.appspot.com/o/uploads%2Fuploads%2F1629291525186.jpg?alt=media&token=588f1c67-8120-4a44-9677-9cfc806a66f9",
                "Highland"));

        mReference.child("cuaHang").push().setValue(new CuaHang("JxZOOK1RzcMM7pL5I6naGZfYSsu2","https://firebasestorage.googleapis.com/v0/b/quanlybanhang-26023.appspot.com/o/uploads%2Fuploads%2F1629326499235.jpg?alt=media&token=b04912a3-fea2-462f-bb9b-8a491c25454c",
                "Trung Nguyên Coffee"));
    }

    public void getAddlist()
    {
        mReference.child("cuaHang").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CuaHang cuaHang=snapshot.getValue(CuaHang.class);
                Log.d("ppp",cuaHang.getName()+"nmb");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendNotification(SanPham sanPham)
    {
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.add);
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
//        String day =simpleDateFormat.format(new Date());
//
//        simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
//
//        String time =simpleDateFormat.format(new Date());

        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.donga_bank_notification);
//        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_large);
        notificationLayout.setTextViewText(R.id.tvMessage,sanPham.getChitiet());
        notificationLayout.setTextViewText(R.id.tvTittle,sanPham.getNameProduct());
        notificationLayout.setImageViewBitmap(R.id.imgLogo,bitmap);
        sanPham.setSoluong(1);
        Intent resultIntent = new Intent(this, SuperQuangCaoActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle=new Bundle();
        bundle.putSerializable("sanpham", sanPham);
        resultIntent.putExtras(bundle);

// Create the TaskStackBuilder and add the intent, which inflates the back stack
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(this,0,resultIntent ,PendingIntent.FLAG_UPDATE_CURRENT);


        RemoteViews notificationLayoutmax = new RemoteViews(getPackageName(), R.layout.donga_bank_notification_max);
//        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_large);
        notificationLayoutmax.setTextViewText(R.id.tvMessagemax,sanPham.getChitiet());
        notificationLayoutmax.setImageViewBitmap(R.id.imgLogo,bitmap);




        Notification notification=new NotificationCompat.Builder(this, Putnotification.CHANNEL_ID)
//                .setContentTitle("this is my application")
//                .setContentText("cmm")
//                .setLargeIcon(bitmap)
                .setTicker("cmm")
                .setContentIntent(resultPendingIntent)
                .setCustomContentView(notificationLayout)
                .setSmallIcon(R.drawable.ic_baseline_message_24)
                .setCustomBigContentView(notificationLayoutmax)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager!=null)
        {
            notificationManager.notify(0,notification);

        }

    }

    private void getSuperQuangCao()
    {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("sanPhamQuangCao");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey();

                for(DataSnapshot snapshot1: snapshot.getChildren())
                {
                    for (DataSnapshot snapshot2: snapshot1.getChildren())
                    {
                        SanPham  sanPham =snapshot2.getValue(SanPham.class);
                        if(sanPham.isSuperquangcao())
                        {
                           sendNotification(sanPham);
                        }

                    }

                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("BBB","onResume Mainactivity");

//        Toast.makeText(getApplicationContext(),khachHang.getIdKhachhang()+"",Toast.LENGTH_SHORT).show();

        products=getCartList(KhachHangActivity.this);




    }

    @Override
    protected void onPause() {
        Log.d("BBB","onPause Mainactivity");
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("BBB","onStop Mainactivity");
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(getApplicationContext(),"Destroy",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }


}