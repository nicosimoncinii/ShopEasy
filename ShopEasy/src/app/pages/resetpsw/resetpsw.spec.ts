import { ComponentFixture, TestBed } from "@angular/core/testing";

import { ResetpswComponent } from "./resetpsw";


describe("Resetpsw", () => {
  let component: ResetpswComponent;
  let fixture: ComponentFixture<ResetpswComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResetpswComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ResetpswComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
