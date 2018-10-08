package com.theworldofluster.example.ziang.tremblenumber.view;

/**
 * Created by liupeng on 2018/9/12.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.utils.DateUtil22;

import java.util.ArrayList;
import java.util.List;

public class SelectWeekPopupWindow {
    private int year;
    private int month;
    private int days;
    private int syear;
    private int smonth;
    private int sdays;
    private int sdaysLiang;
    private int xyear;
    private int xmonth;
    private int xdays;
    private int xdaysLiang;
    private int day;
    public int dayData;
    public int yearData;
    public int monthData;
    private int daysLiang;
    private int firstDay;
    private PopupWindow popupWindow;
    private Context mContext;
    private View layoutContent;
    private ListView listView;
    private List<int[]> list = new ArrayList();
    private SelectWeekPopupWindow.OnSelectItemClickListencer onSelectItemClickListencer;
    private int selectPosition = -1;
    private boolean isZxStyle;
    private boolean isCenter;
    private TextView title,title_year;
    private boolean hasHead;
    private boolean hasFoot;
    BaseAdapter adapter = new BaseAdapter() {
        public int getCount() {
            return SelectWeekPopupWindow.this.list.size();
        }

        public Object getItem(int i) {
            return SelectWeekPopupWindow.this.list.get(i);
        }

        public long getItemId(int i) {
            return (long)i;
        }

        public View getView(final int i, View view, ViewGroup viewGroup) {
            SelectWeekPopupWindow.ViewHolder viewHolder = null;
            if(view == null) {
                viewHolder = SelectWeekPopupWindow.this.new ViewHolder();
                view = LayoutInflater.from(SelectWeekPopupWindow.this.mContext).inflate(R.layout.item_select_week, (ViewGroup)null);
                viewHolder.mImageViewBg = (ImageView)view.findViewById(R.id.bg);
                viewHolder.ts[0] = (TextView)view.findViewById(R.id.t1);
                viewHolder.ts[1] = (TextView)view.findViewById(R.id.t2);
                viewHolder.ts[2] = (TextView)view.findViewById(R.id.t3);
                viewHolder.ts[3] = (TextView)view.findViewById(R.id.t4);
                viewHolder.ts[4] = (TextView)view.findViewById(R.id.t5);
                viewHolder.ts[5] = (TextView)view.findViewById(R.id.t6);
                viewHolder.ts[6] = (TextView)view.findViewById(R.id.t7);
                view.setTag(viewHolder);
            } else {
                viewHolder = (SelectWeekPopupWindow.ViewHolder)view.getTag();
            }

            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SelectWeekPopupWindow.this.dayData = ((int[])SelectWeekPopupWindow.this.list.get(i))[0];
                    SelectWeekPopupWindow.this.monthData = SelectWeekPopupWindow.this.month;
                    SelectWeekPopupWindow.this.yearData = SelectWeekPopupWindow.this.year;
                    if(i == 0 && SelectWeekPopupWindow.this.hasHead) {
                        if(SelectWeekPopupWindow.this.monthData == 1) {
                            SelectWeekPopupWindow.this.monthData = 12;
                            --SelectWeekPopupWindow.this.yearData;
                        } else {
                            --SelectWeekPopupWindow.this.monthData;
                        }
                    }

                    if(i == SelectWeekPopupWindow.this.list.size() - 1 && SelectWeekPopupWindow.this.hasFoot) {
                        if(SelectWeekPopupWindow.this.monthData == 12) {
                            SelectWeekPopupWindow.this.monthData = 1;
                            ++SelectWeekPopupWindow.this.yearData;
                        } else {
                            ++SelectWeekPopupWindow.this.monthData;
                        }
                    }

                    SelectWeekPopupWindow.this.onSelectItemClickListencer.onSelectItemClick(view, i);
                    SelectWeekPopupWindow.this.popupWindow.dismiss();
                }
            });

            int j;
            for(j = 0; j < 7; ++j) {
                viewHolder.ts[j].setAlpha(1.0F);
                viewHolder.ts[j].setTextColor(Color.WHITE);
                viewHolder.ts[j].setText(((int[])SelectWeekPopupWindow.this.list.get(i))[j] + "");
            }

            if(i == 0) {
                for(j = 0; j < 7; ++j) {
                    if(((int[])SelectWeekPopupWindow.this.list.get(i))[j] > 10) {
                        SelectWeekPopupWindow.this.hasHead = true;
                        viewHolder.ts[j].setAlpha(0.5F);
                    }
                }
            }

            if(i == SelectWeekPopupWindow.this.list.size() - 1) {
                for(j = 0; j < 7; ++j) {
                    if(((int[])SelectWeekPopupWindow.this.list.get(i))[j] < 10) {
                        SelectWeekPopupWindow.this.hasFoot = true;
                        viewHolder.ts[j].setAlpha(0.5F);
                    }
                }
            }

            if(i == SelectWeekPopupWindow.this.selectPosition) {
                viewHolder.mImageViewBg.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mImageViewBg.setVisibility(View.INVISIBLE);
            }

            return view;
        }
    };

    public SelectWeekPopupWindow(Context context, SelectWeekPopupWindow.OnSelectItemClickListencer onSelectItemClickListencer, int year, int month, int day) {
        this.mContext = context;
        this.list = new ArrayList();
        this.onSelectItemClickListencer = onSelectItemClickListencer;
        this.init();
    }

    public void setZXStyle(boolean isCenter) {
        this.isZxStyle = true;
        this.isCenter = isCenter;
    }

    private void setSX() {
        this.syear = this.year;
        this.xyear = this.year;
        this.smonth = this.month - 1;
        this.xmonth = this.month + 1;
        if(this.smonth == 0) {
            this.smonth = 12;
            --this.syear;
        }

        if(this.xmonth == 13) {
            this.xmonth = 1;
            ++this.xyear;
        }

        this.xdays = DateUtil22.getMonthDays(this.xyear, this.xmonth - 1);
        this.sdays = DateUtil22.getMonthDays(this.syear, this.smonth - 1);
    }

    private void getSum() {
        this.setSX();
        this.days = DateUtil22.getMonthDays(this.year, this.month - 1);
        this.firstDay = DateUtil22.getFirstDayWeek17(this.year, this.month - 1);
        this.daysLiang = this.days;
        --this.daysLiang;
        this.list.clear();
        int[] is1 = new int[7];

        for(int i = 0; i < 8 - this.firstDay; ++i) {
            is1[this.firstDay - 1 + i] = this.days - this.daysLiang;
            --this.daysLiang;
            if(this.day == is1[this.firstDay - 1 + i]) {
                this.selectPosition = 0;
            }
        }

        this.list.add(is1);
        int[] is2 = new int[7];

        for(int i = 0; i < 7; ++i) {
            is2[i] = this.days - this.daysLiang;
            --this.daysLiang;
            if(this.day == is2[i]) {
                this.selectPosition = 1;
            }
        }

        this.list.add(is2);
        int[] is3 = new int[7];

        for(int i = 0; i < 7; ++i) {
            is3[i] = this.days - this.daysLiang;
            --this.daysLiang;
            if(this.day == is3[i]) {
                this.selectPosition = 2;
            }
        }

        this.list.add(is3);
        int[] is4 = new int[7];

        for(int i = 0; i < 7; ++i) {
            is4[i] = this.days - this.daysLiang;
            --this.daysLiang;
            if(this.day == is4[i]) {
                this.selectPosition = 3;
            }

            if(this.daysLiang < 0) {
                this.list.add(is4);
                return;
            }
        }

        this.list.add(is4);
        int[] is5 = new int[7];

        for(int i = 0; i < 7; ++i) {
            is5[i] = this.days - this.daysLiang;
            --this.daysLiang;
            if(this.day == is5[i]) {
                this.selectPosition = 4;
            }

            if(this.daysLiang < 0) {
                this.list.add(is5);
                return;
            }
        }

        this.list.add(is5);
        if(this.daysLiang >= 0) {
            int[] is6 = new int[7];

            for(int i = 0; i < 7; ++i) {
                is6[i] = this.days - this.daysLiang;
                --this.daysLiang;
                if(this.day == is6[i]) {
                    this.selectPosition = 5;
                }

                if(this.daysLiang < 0) {
                    this.list.add(is6);
                    return;
                }
            }

            this.list.add(is6);
        }
    }

    private void getSumNextLast() {
        this.sdaysLiang = 0;

        int i;
        for(i = 0; i < 7; ++i) {
            if(((int[])this.list.get(0))[i] == 0) {
                ++this.sdaysLiang;
            }
        }

        for(i = 0; i < this.sdaysLiang; ++i) {
            ((int[])this.list.get(0))[i] = this.sdays - this.sdaysLiang + 1 + i;
        }

        this.xdaysLiang = 0;

        for(i = 0; i < 7; ++i) {
            if(((int[])this.list.get(this.list.size() - 1))[i] == 0) {
                ++this.xdaysLiang;
            }
        }

        i = this.xdaysLiang;

        for(int j = 0; j < this.xdaysLiang; ++j) {
            int p = 6 - j;
            ((int[])this.list.get(this.list.size() - 1))[p] = i--;
        }

    }

    private void init() {
        this.getSum();
        this.getSumNextLast();
        this.hasHead = false;
        this.hasFoot = false;
        if(this.layoutContent == null) {
            this.layoutContent = LayoutInflater.from(this.mContext).inflate(R.layout.layout_select_week, (ViewGroup)null);
            this.listView = (ListView)this.layoutContent.findViewById(R.id.list_view);
            this.title = (TextView)this.layoutContent.findViewById(R.id.titile);
            this.title_year = (TextView)this.layoutContent.findViewById(R.id.title_year);
            this.listView.setAdapter(this.adapter);
            this.adapter.notifyDataSetChanged();
            this.title.setText(this.month + "月");
            this.title_year.setText(this.year + "年");
            this.popupWindow = new PopupWindow(this.layoutContent, -1, -1, true);
            this.popupWindow.setTouchable(true);
            this.popupWindow.setOutsideTouchable(false);
            this.popupWindow.setBackgroundDrawable(new BitmapDrawable());
            this.layoutContent.findViewById(R.id.imgup).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SelectWeekPopupWindow.this.year = SelectWeekPopupWindow.this.syear;
                    SelectWeekPopupWindow.this.month = SelectWeekPopupWindow.this.smonth;
                    SelectWeekPopupWindow.this.selectPosition = -1;
                    if(SelectWeekPopupWindow.this.year == SelectWeekPopupWindow.this.yearData && SelectWeekPopupWindow.this.month == SelectWeekPopupWindow.this.monthData) {
                        SelectWeekPopupWindow.this.day = SelectWeekPopupWindow.this.dayData;
                    } else {
                        SelectWeekPopupWindow.this.day = -1;
                    }

                    SelectWeekPopupWindow.this.init();
                }
            });
            this.layoutContent.findViewById(R.id.imgdown).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SelectWeekPopupWindow.this.year = SelectWeekPopupWindow.this.xyear;
                    SelectWeekPopupWindow.this.month = SelectWeekPopupWindow.this.xmonth;
                    SelectWeekPopupWindow.this.selectPosition = -1;
                    if(SelectWeekPopupWindow.this.year == SelectWeekPopupWindow.this.yearData && SelectWeekPopupWindow.this.month == SelectWeekPopupWindow.this.monthData) {
                        SelectWeekPopupWindow.this.day = SelectWeekPopupWindow.this.dayData;
                    } else {
                        SelectWeekPopupWindow.this.day = -1;
                    }

                    SelectWeekPopupWindow.this.init();
                }
            });
            //
            this.layoutContent.findViewById(R.id.imgup_year).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SelectWeekPopupWindow.this.year = SelectWeekPopupWindow.this.syear-1;
//                    SelectWeekPopupWindow.this.month = SelectWeekPopupWindow.this.smonth;
                    SelectWeekPopupWindow.this.selectPosition = -1;
                    if(SelectWeekPopupWindow.this.year == SelectWeekPopupWindow.this.yearData && SelectWeekPopupWindow.this.month == SelectWeekPopupWindow.this.monthData) {
                        SelectWeekPopupWindow.this.day = SelectWeekPopupWindow.this.dayData;
                    } else {
                        SelectWeekPopupWindow.this.day = -1;
                    }

                    SelectWeekPopupWindow.this.init();
                }
            });
            this.layoutContent.findViewById(R.id.imgdown_year).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SelectWeekPopupWindow.this.year = SelectWeekPopupWindow.this.xyear+1;
//                    SelectWeekPopupWindow.this.month = SelectWeekPopupWindow.this.xmonth;
                    SelectWeekPopupWindow.this.selectPosition = -1;
                    if(SelectWeekPopupWindow.this.year == SelectWeekPopupWindow.this.yearData && SelectWeekPopupWindow.this.month == SelectWeekPopupWindow.this.monthData) {
                        SelectWeekPopupWindow.this.day = SelectWeekPopupWindow.this.dayData;
                    } else {
                        SelectWeekPopupWindow.this.day = -1;
                    }

                    SelectWeekPopupWindow.this.init();
                }
            });
            //
            this.layoutContent.findViewById(R.id.bottom).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SelectWeekPopupWindow.this.popupWindow.dismiss();
                }
            });
        } else {
            this.title.setText(this.month + "月");
            this.title_year.setText(this.year + "月");
            this.adapter.notifyDataSetChanged();
        }

    }

    public void diss() {
        this.popupWindow.dismiss();
    }

    public void show(int year, int month, int day, View targetView, OnDismissListener onDismissListener) {
        this.popupWindow.showAsDropDown(targetView);
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayData = day;
        this.yearData = year;
        this.monthData = month;
        this.init();
        this.popupWindow.setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
                SelectWeekPopupWindow.this.popupWindow.dismiss();
            }
        });
        if(onDismissListener != null) {
            this.popupWindow.setOnDismissListener(onDismissListener);
        }

        this.listView.setSelection(this.selectPosition);
    }

    class ViewHolder {
        ImageView mImageViewBg;
        TextView[] ts = new TextView[7];

        ViewHolder() {
        }
    }

    public interface OnSelectItemClickListencer {
        void onSelectItemClick(View var1, int var2);
    }
}

