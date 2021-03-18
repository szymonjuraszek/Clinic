import {LogLevel} from "./log.service";


export class LogEntry {

  entryDate: Date = new Date();

  message: string = "";

  // @ts-ignore
  level: LogLevel = LogLevel.Debug;

  extraInfo: any[] = [];

  logWithDate: boolean = true;

  buildLogString(): string {
    let ret: string = "";

    if (this.logWithDate) {
      ret = new Date() + "\n - ";
    }

    ret += "Type: " + LogLevel[this.level] + '\n';
    ret += " - Message: " + this.message + '\n';
    if (this.extraInfo.length) {
      ret += " - Extra Info: " + this.formatParams(this.extraInfo);
    }

    return ret;
  }

  private formatParams(params: any[]): string {
    let ret: string = params.join(",");

    // Is there at least one object in the array?
    if (params.some(p => typeof p == "object")) {
      ret = "";

      // Build comma-delimited string
      for (let item of params) {
        ret += JSON.stringify(item) + ",";
      }
    }

    return ret;
  }
}
