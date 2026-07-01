import { ComponentFixture, TestBed } from "@angular/core/testing";

import { StoricoOrdini } from "./storico-ordini";

describe("StoricoOrdini", () => {
  let component: StoricoOrdini;
  let fixture: ComponentFixture<StoricoOrdini>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StoricoOrdini],
    }).compileComponents();

    fixture = TestBed.createComponent(StoricoOrdini);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
