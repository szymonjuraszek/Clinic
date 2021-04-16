export class Address {

  street?: string;

  zip_code?: string;

  city?: string;

  constructor(street: string, zip_code: string, city: string) {
    this.street = street;
    this.zip_code = zip_code;
    this.city = city;
  }
}
