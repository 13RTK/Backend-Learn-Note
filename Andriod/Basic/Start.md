# 一、视图(View)

- UI: 与用户交互的窗口(User Interface)
- IDE: 集成开发环境
- 视图:应用窗口的组成







## 1) 种类

- TextView: 文本，用于在屏幕显示文字
- ImagineView: 图片
- Botten: 按钮

****

















# 二、XML

- XML: 可拓展标记语言(Extensive Markup Language)





## 1) Syntax

eg:

```xml
<TextView
          android:text=""
          android:layout_width=""
					 android:layout_width=""/>
```

构成:

- 尖括号，视图名称(TextView大驼峰)
- 属性名=属性值
- 闭合(TextView为自闭Self_Closing)
- 长宽最好使用dp(密度独立像素)，使用像素pixel在不同分辨率的设备上大小不同









****



# 三、TextView

文字视图，用于显示文字





## 1) 视图边界/大小

```xml
<TextView
          android:text=""
          android:layout_width="XXdp"
          android:layout_height="XXdp"/>
```

- 对于指定具体dp数值的写法，我们成为硬编码，如果文本超出其返回，就会换行显示

- 对于不同长度文本的适应性很差，推荐使用wrap_content代替，之后不再需要手动修改







## 2) 文字内容

```xml
<TextView
          android:text=""/>
```















## 3) 文字大小

```xml
<TextView
          android:textSize="45sp"/>
```

- 只有使用sp作为单位，才能让用户自用调节字体大小











## 4) 背景颜色

```xml
<TextView
          android:background=""/>
```

- 可以直接使用颜色的16进制数











## 5) 字体颜色

```xml
<TextView
          android:textColor=""/>
```

- 和背景一样，可以使用16进制颜色数值











## 6) 文字样式

```xml
<TextView
          android:textStyle="bold/italic"
          android:textAllCaps="true/false"/>
```

****













# 四、ImageView

- 图片视图，用于显示图片





## 1) 图片来源

```xml
<ImageView
           android:src="@app_name/file_name"/>
```





## 2) 图片视图大小

```xml
<ImageView
           android:layout_width="XXdp/wrap_content"
           android:layout_height="XXdp/wrap_content"/>
```











## 3) 图片位置

```xml
<ImagineView
							android:scaleType="center/centerCrop"/>
```

- center: 居中显示
- centerCrop: 拉伸居中裁切显示(不留空白)













