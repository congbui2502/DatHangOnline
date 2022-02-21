package java.android.quanlybanhang.CongAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.Sonclass.SanPham;
import java.android.quanlybanhang.Activity.KhachHangActivity;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public  class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public static  List<SanPham> products;
    private KhachHangActivity mainActivity;

    public SetPos setPos;

    public  interface  SetPos{
        void setPos(int size);

    }
//    private long build;


    public  IclickAddToCartListener iclickAddToCartListener;

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    private Boolean flag;

    private  List<ProductViewHolder> holderList=new ArrayList<ProductViewHolder>();

    public interface IclickAddToCartListener{
        void onClickAddToCart(ImageView imageToCart,SanPham product);
        void setGiaDonHang(long donHang);
    }

    public void setData(KhachHangActivity mainActivity, IclickAddToCartListener listener,SetPos setPos)
    {
        this.mainActivity=mainActivity;
        this.iclickAddToCartListener=listener;
        notifyDataSetChanged();
        this.setPos=setPos;


    }


    public void  setListCart()
    {
        products=new ArrayList<SanPham>();
        DatabaseReference mReference1= FirebaseDatabase.getInstance().getReference();
        mReference1.child("gioHang").child(KhachHangActivity.khachHang.getIdKhachhang()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot snapshot1: snapshot.getChildren())
                {

                    if (snapshot1.getKey().equals("sanPham"))
                    {
                        for (DataSnapshot snapshot3:snapshot1.getChildren())
                        {
                            SanPham sanPham=snapshot3.getValue(SanPham.class);

                            products.add(sanPham);
                            notifyDataSetChanged();
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

    public ProductAdapter(List<SanPham> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        this.build=0;
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        SanPham product=products.get(position);
        holderList.add(holder);
        Log.d("QQQ",holderList.size()+"");
        int abc=position;

        if(product!=null)
        {
            holder.tenMon.setText(product.getNameProduct());
            holder.gia.setText(Cart_Fragment.addDauPhay(product.getDonGia().get(0).getGiaBan())+" VND");
            holder.soLuong.setText(product.getSoluong()+"");
            holder.tvLoai.setText(product.getDonGia().get(0).getTenDonGia());
            holder.btnTru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String so=holder.soLuong.getText().toString();
                    int i=Integer.parseInt(so);

                    if(i==0)
                    {
                        holder.btnTru.setEnabled(false);
                        holder.soLuong.setText(i+"");
                        holder.gia.setText(Cart_Fragment.addDauPhay(product.getDonGia().get(0).getGiaBan()*i)+" VND");
                        products.get(abc).setSoluong(i);

                        iclickAddToCartListener.setGiaDonHang(setGiaDonHang());

                    }else {
                        i--;
                        holder.soLuong.setText(i+"");
                        holder.gia.setText(Cart_Fragment.addDauPhay(product.getDonGia().get(0).getGiaBan()*i)+" VND");
                        products.get(abc).setSoluong(i);
                        iclickAddToCartListener.setGiaDonHang(setGiaDonHang());
                        if (i==0)
                        {
                            holder.btnTru.setEnabled(false);
                        }
                    }


                }
            });
            holder.btnCong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String so=holder.soLuong.getText().toString();
                    int i=Integer.parseInt(so);

                    if(i==0)
                    {
                        holder.btnTru.setEnabled(true);
                        i++;
                        holder.soLuong.setText(i+"");
                        holder.gia.setText(Cart_Fragment.addDauPhay(product.getDonGia().get(0).getGiaBan()*i)+" VND");
                        products.get(abc).setSoluong(i);
                        iclickAddToCartListener.setGiaDonHang(setGiaDonHang());

                    }else {
                        i++;
                        holder.soLuong.setText(i+"");
                        holder.gia.setText(Cart_Fragment.addDauPhay(product.getDonGia().get(0).getGiaBan()*i)+" VND");
                        products.get(abc).setSoluong(i);

                        iclickAddToCartListener.setGiaDonHang(setGiaDonHang());
                    }

                }
            });
//            holder.imgaddTocart.setImageResource(R.drawable.highland_cofee);

            Picasso.get().load(product.getImgProduct().toString()).into(holder.imgaddTocart);

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.checkBox.isChecked()==true)
                    {
                        holder.checkBox.setChecked(true);
                        holder.flag=1;
                        iclickAddToCartListener.setGiaDonHang(setGiaDonHang());
                    } else {
                        holder.checkBox.setChecked(false);
                        holder.flag=0;
                        iclickAddToCartListener.setGiaDonHang(setGiaDonHang());
                    }
                    Log.d("mmm",holder.flag+"");
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        products.remove(holder.getAdapterPosition());
                        holderList.remove(holder.getAdapterPosition());

                        notifyItemRemoved(holder.getAdapterPosition());
                    AHNotification notification = new AHNotification.Builder()
                            .setText(String.valueOf(products.size()))
                            .build();
                     mainActivity.getBottomNavigation().setNotification(notification, 1);

                }
            });



        }

    }


    @Override
    public void onViewRecycled(@NonNull ProductViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        if (this.products!=null)
        {
            setPos.setPos(products.size());
            return products.size();
        }

        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {


        private TextView tenMon;
        private TextView gia;
        private TextView soLuong;
        private TextView delete;
        private TextView tvLoai;
        private int flag;

        private Button btnCong;
        private Button btnTru;
        private CheckBox checkBox;
        private CircleImageView imgaddTocart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox=itemView.findViewById(R.id.rdProduct);
            imgaddTocart=itemView.findViewById(R.id.crImgProductCart);
            tenMon = itemView.findViewById(R.id.tvCartProduct);
            gia =itemView.findViewById(R.id.tvGiaProduct);
            btnTru=itemView.findViewById(R.id.btnTru);
            soLuong=itemView.findViewById(R.id.tvSoLuongSanPham);
            btnCong=itemView.findViewById(R.id.btnCong);
            delete=itemView.findViewById(R.id.tvDelete);
            tvLoai=itemView.findViewById(R.id.tvLoai);
            flag=-1;



        }
    }

    public void setCheckedCheckbox(Boolean aBoolean)
    {
        if(aBoolean==true)
        {
            for (int i = 0; i < holderList.size(); i++) {
                holderList.get(i).checkBox.setChecked(true);
            }

        }else {
            for (int i = 0; i < holderList.size(); i++) {
                holderList.get(i).checkBox.setChecked(false);
            }

        }
    }

    public List<SanPham> getListProductIsChecked()
    {
        List<SanPham> dsSP=new ArrayList<SanPham>();

        for (int i = 0; i < holderList.size(); i++) {
            if (holderList.get(i).checkBox.isChecked()==true)
            {
                dsSP.add(products.get(i));
                Log.d("QQQ",dsSP.size()+"");

            }
        }



        return dsSP;
    }

    public long setGiaDonHang()
    {
        long build=0;

        for (int i = 0; i <holderList.size() ; i++) {
            if(holderList.get(i).checkBox.isChecked())
            {
                build=build+products.get(i).getDonGia().get(0).getGiaBan()*products.get(i).getSoluong();
            }
//            else if(holderList.get(i).checkBox.isChecked()==false && holderList.get(i).flag==0){
//                build=build-products.get(i).getGiaBan()*products.get(i).getSoluong();
//            }

        }
        return build;
    }
}
