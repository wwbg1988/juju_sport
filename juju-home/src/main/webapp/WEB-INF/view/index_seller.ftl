<h3>商家店铺</h3>
<div>店铺信息</div>
<p>
    店铺信息描述
</p>
店铺:${root.shopName}
店铺QQ:${root.qq}
店铺积分:${root.score}
<#list root.items as item>
    ${item.itemName}
</#list>