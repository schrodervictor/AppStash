package io.github.appstash.shop.ui.panel.product;

import io.github.appstash.shop.service.product.model.ProductInfo;
import io.github.appstash.shop.ui.panel.base.AbstractPizzaShopBasePanel;
import io.github.appstash.shop.ui.panel.base.HighLightBehavior;
import org.apache.commons.collections.CollectionUtils;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductItemListPanel extends AbstractPizzaShopBasePanel {

    private IModel<?> containerTopic;
    private IModel<List<ProductInfo>> productListModel;

    public ProductItemListPanel(String id, String recommenderType, IModel<?> containerTopic, IModel<List<ProductInfo>> productListModel) {

        super(id, productListModel);


        this.containerTopic = containerTopic;
        this.productListModel = productListModel;

        add(listWrapper(recommenderType));

        setOutputMarkupId(true);
    }

    private Component listWrapper(String recommenderType) {
        WebMarkupContainer listWrapper = new WebMarkupContainer("listWrapper");
        listWrapper.add(topicLabel());
        listWrapper.add(productList(recommenderType));
        return listWrapper.add(new HighLightBehavior());
    }

    @Override
    protected void onConfigure() {
        setVisible(CollectionUtils.isNotEmpty(productListModel.getObject()));
    }

    private Label topicLabel() {
        return new Label("productsTopic", containerTopic);
    }

    private DataView<ProductInfo> productList(final String parentTag) {
        DataView<ProductInfo> productsView = new DataView<ProductInfo>("products", productsProvider()) {
            @Override
            protected void populateItem(Item<ProductInfo> item) {
                item.add(newProductItemPanel("product", parentTag, item.getModel()));
            }
        };
        productsView.add(new HighLightBehavior());
        return productsView;
    }

    protected Component newProductItemPanel(String id, String parentTag, IModel<ProductInfo> model) {
        return new ProductItemPanel(id, model, createTags(parentTag));
    }

    private IDataProvider<ProductInfo> productsProvider() {
        return new IDataProvider<ProductInfo>() {
            @Override
            public Iterator<ProductInfo> iterator(long first, long count) {
                return productListModel.getObject().iterator();
            }

            @Override
            public long size() {
                return productListModel.getObject().size();
            }

            @Override
            public IModel<ProductInfo> model(ProductInfo product) {
                return Model.of(product);
            }

            @Override
            public void detach() {
            }
        };
    }


    protected List<String> getTags(String parentTag) {
        return new ArrayList<>();
    }

    protected ListModel<String> createTags(String parentTag) {
        ArrayList<String> tagList = new ArrayList<>(1);
        tagList.add(parentTag);
        return new ListModel<>(tagList);
    }
}
