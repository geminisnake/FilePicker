package com.alienleeh.filepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlienLeeH on 2016/2/14..Hour:23
 * Email:alienleeh@foxmail.com
 * Description:浏览文件Activity
 */
public class FileBrowserActivity extends AppCompatActivity {

    private ListView filesView;
    private static final String ROOT_PATH =
            Environment.getExternalStorageDirectory().getPath() + "/";
    public static final String EXTRA_DATA_PATH = "EXTRA_DATA_PATH";
    private List<String> paths;
    private List<String> names;
    private List<FileManagerItem> fileListItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_one_listview);
        filesView = (ListView) findViewById(R.id.lisview_in_just);

        showFilesDir(ROOT_PATH);
    }


    private void showFilesDir(String path) {
        names = new ArrayList<>();
        paths = new ArrayList<>();
        File file = new File(path);
        File[] files = file.listFiles();
        if (path.equals(ROOT_PATH)) {
            names.add("@1");
            paths.add(path);
        } else {
            names.add("@2");
            paths.add(file.getParent());
        }
        for (File f : files) {
            names.add(f.getName());
            paths.add(f.getPath());
        }
        fileListItems.clear();
        for (int i = 0; i < names.size(); i++) {
            fileListItems.add(new FileManagerItem(names.get(i), paths.get(i)));
        }
        filesView.setAdapter(new FilesBrowserAdapter(this, fileListItems));
        filesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String path = fileListItems.get(i).getPath();
                File file = new File(path);
                if (file.exists() && file.canRead()){
                    if (file.isDirectory()){
                        showFilesDir(path);
                    }else {
                        selectFile(path);
                    }
                }else {
                    Toast.makeText(FileBrowserActivity.this,"没有权限",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void start(Activity act,int RequestCode){
        Intent intent = new Intent(act,FileBrowserActivity.class);
        act.startActivityForResult(intent,RequestCode);

    }
    private void selectFile(String path) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATA_PATH,path);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }


    private class FileManagerItem {
        String name;
        String path;

        public FileManagerItem(String name, String path) {
            this.name = name;
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public String getPath() {
            return path;
        }
    }

    private class FilesBrowserAdapter extends MyBaseAdapter {

        public FilesBrowserAdapter(Context context, List<?> list) {
            super(context, list);
        }

        @Override
        public View initView(int position, View convertView, ViewGroup viewGroup) {
            ItemHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.file_browser_list_item, viewGroup, false);
                holder = new ItemHolder(convertView);
                convertView.setTag(holder);
            }
            holder = (ItemHolder) convertView.getTag();
            FileManagerItem item = (FileManagerItem) getItem(position);
            if (item.getName().equals("@1")){
                holder.tv.setText("/");
                holder.iv.setImageDrawable(getDrawable(R.drawable.directory));
//                ImageLoaderHelper.displayAssetDef("file_picker/directory.png",holder.iv);
            }else if (item.getName().equals("@2")){
                holder.tv.setText("...");
                holder.iv.setImageDrawable(getDrawable(R.drawable.directory));
//                ImageLoaderHelper.displayAssetDef("file_picker/directory.png",holder.iv);
            }else {
                File f = new File(item.getPath());
                holder.tv.setText(item.getName());
                if (f.isFile()){
                    holder.iv.setImageDrawable(getDrawable(R.drawable.file));
//                    ImageLoaderHelper.displayAssetDef("file_picker/file.png",holder.iv);
                }else {
                    holder.iv.setImageDrawable(getDrawable(R.drawable.directory));
//                    ImageLoaderHelper.displayAssetDef("file_picker/directory.png",holder.iv);
                }
            }
            return convertView;
        }

        private class ItemHolder {
            ImageView iv;
            TextView tv;

            public ItemHolder(View v) {
                this.iv = (ImageView) v.findViewById(R.id.file_image);
                this.tv = (TextView) v.findViewById(R.id.file_name);
            }
        }
    }
}
