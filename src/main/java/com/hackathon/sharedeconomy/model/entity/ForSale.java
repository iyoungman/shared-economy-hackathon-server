package com.hackathon.sharedeconomy.model.entity;

/**
 * Created by YoungMan on 2019-02-14.
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hackathon.sharedeconomy.model.dto.ForSaleDto;
import com.hackathon.sharedeconomy.model.enums.SaleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "for_sale_tbl")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ForSale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long price;

	private String name;

	private String preferredSex;

	@Embedded
	private AdditionalInfo additionalInfo;

	@Enumerated(EnumType.STRING)
	private SaleType saleType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

	@OneToMany(mappedBy = "forSale", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Image> images = new ArrayList<>();

	@OneToMany(mappedBy = "forSale")
	@JsonManagedReference
	private List<Shopping> shoppings = new ArrayList<>();

	@Builder
	public ForSale(Long price, String name, String preferredSex, AdditionalInfo additionalInfo, User user, List<Image> images, List<Shopping> shoppings) {
		this.price = price;
		this.name = name;
		this.preferredSex = preferredSex;
		this.additionalInfo = additionalInfo;
		this.saleType = SaleType.SALE;
		this.user = user;
		this.images = images;
		this.shoppings = shoppings;
	}

	public void updateForSale(ForSaleDto.RegisterRequest registerDto) {
		this.price = registerDto.getPrice();
		this.name = registerDto.getName();
		this.additionalInfo.updateAdditionalInfo(registerDto.getPrecautionFirst(), registerDto.getPrecautionSecond(), registerDto.getOfferFirst(), registerDto.getOfferSecond());
	}

	public void updateSaleType() {
		this.saleType = SaleType.COMPLETE;
	}
}
