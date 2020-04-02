# ScaleImgHeader
CoordinatorLayout实现拉伸header动画
该动画展示：
使用coordinatorlayout 和自定义behavior 联合实现

### 问题记录：
1. ViewCompat.setTopAndBottom(view, position); 

  onlayoutchild 调用起来这个api 没有办法直接实现位置的定位，但是，

  onNestedPreScroll 这个api 可以移动

2. xml 中使用以下api 在预览图可以达到效果， 但是在真机运行过程中不可以
  
  app:layout_anchorGravity="bottom"
  
  app:layout_anchor="@id/img_bg"
  
  解决方案： 在behavior onlayoutchild 中自己去定义位置
  
  
