import {User} from "./User";
import {PlaceVisitSettings} from "./PlaceVisitSettings";

export interface Doctor extends User {

  id?: string;

  specialization?: string;

  degree?: string;

  placeVisitSettingsArray: Array<PlaceVisitSettings>;
}
