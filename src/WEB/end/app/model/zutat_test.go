package model

import (
	"database/sql"
	"reflect"
	"strconv"
	"testing"

	_ "github.com/go-sql-driver/mysql"
)

func TestInsertZutaten(t *testing.T) {
	BeginnTest()
	trans, _ := btDB.Begin()
	_, err := trans.Exec("DELETE FROM `rezept2zutat`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("DELETE FROM `zutat`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE zutat AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	trans.Commit()
	trans, _ = btDB.Begin()
	zutat1 := Zutat{
		Name:    "Wasser",
		Einheit: "ml",
		Menge:   100,
	}
	zutaten := []Zutat{zutat1}
	erwartet := []int64{1}
	type args struct {
		z     []Zutat
		trans *sql.Tx
	}
	tests := []struct {
		name string
		args args
		want []int64
	}{
		// TODO: Add test cases.
		//TF124
		{name: "Zutat",
			args: args{z: zutaten,
				trans: trans},
			want: erwartet,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := InsertZutaten(tt.args.z, tt.args.trans); !reflect.DeepEqual(got, tt.want) {
				t.Errorf("InsertZutaten() = %v, want %v", got, tt.want)
			}
		})
	}
	trans.Commit()
	EndTest()
}

func TestGetZutaten(t *testing.T) {
	BeginnTest()
	trans, _ := btDB.Begin()
	_, err := trans.Exec("DELETE FROM `rezept2zutat`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("DELETE FROM `zutat`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE zutat AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	trans.Commit()
	trans, _ = btDB.Begin()
	zutat1 := Zutat{
		Name:    "Wasser",
		Einheit: "ml",
	}
	zutat2 := Zutat{
		Name:    "Milch",
		Einheit: "ml",
	}
	zutaten := []Zutat{zutat1, zutat2}
	var zutaten2 []Zutat
	ids := InsertZutaten(zutaten, trans)
	trans.Commit()
	zutaten[0].ID = strconv.FormatInt(ids[0], 10)
	zutaten[1].ID = strconv.FormatInt(ids[1], 10)
	abfrage := []string{strconv.FormatInt(ids[0], 10), strconv.FormatInt(ids[1], 10)}
	abfrage2 := []string{"0"}
	type args struct {
		ids []string
	}
	tests := []struct {
		name string
		args args
		want []Zutat
	}{
		// TODO: Add test cases.
		//TF125
		{name: "Zutaten existieren",
			args: args{ids: abfrage},
			want: zutaten},
		//TF126
		{name: "Zutaten existieren nicht",
			args: args{ids: abfrage2},
			want: zutaten2},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := GetZutaten(tt.args.ids); !reflect.DeepEqual(got, tt.want) {
				t.Errorf("GetZutaten() = %v, want %v", got, tt.want)
			}
		})
	}
	EndTest()
}

func TestDeleteZutaten(t *testing.T) {
	BeginnTest()
	trans, _ := btDB.Begin()
	_, err := trans.Exec("DELETE FROM `rezept2zutat`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("DELETE FROM `zutat`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE zutat AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	trans.Commit()
	trans, _ = btDB.Begin()
	zutat1 := Zutat{
		Name:    "Wasser",
		Einheit: "ml",
	}
	zutat2 := Zutat{
		Name:    "Milch",
		Einheit: "ml",
	}
	zutaten := []Zutat{zutat1, zutat2}
	ids := InsertZutaten(zutaten, trans)
	trans.Commit()
	zutaten[0].ID = strconv.FormatInt(ids[0], 10)
	zutaten[1].ID = strconv.FormatInt(ids[1], 10)
	abfrage := []string{strconv.FormatInt(ids[0], 10), strconv.FormatInt(ids[1], 10)}
	abfrage2 := []string{"0", "-1"}
	trans, _ = btDB.Begin()
	type args struct {
		ids   []string
		trans *sql.Tx
	}
	tests := []struct {
		name string
		args args
		want bool
	}{
		// TODO: Add test cases.
		//TF127
		{name: "Zutaten existieren",
			args: args{ids: abfrage,
				trans: trans},
			want: true},
		//TF128
		{name: "Zutaten existieren nicht",
			args: args{ids: abfrage2,
				trans: trans},
			want: false},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := DeleteZutaten(tt.args.ids, tt.args.trans); got != tt.want {
				t.Errorf("DeleteZutaten() = %v, want %v", got, tt.want)
			}
		})
	}
	trans.Commit()
	EndTest()
}

func TestUpdateZutaten(t *testing.T) {
	BeginnTest()
	trans, _ := btDB.Begin()
	_, err := trans.Exec("DELETE FROM `rezept2zutat`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("DELETE FROM `zutat`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE zutat AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	trans.Commit()
	trans, _ = btDB.Begin()
	zutat1 := Zutat{
		Name:    "Wasser",
		Einheit: "ml",
	}
	zutat2 := Zutat{
		Name:    "Milch",
		Einheit: "ml",
	}
	zutaten := []Zutat{zutat1, zutat2}
	ids := InsertZutaten(zutaten, trans)
	trans.Commit()
	zutaten[0].ID = strconv.FormatInt(ids[0], 10)
	zutaten[1].ID = strconv.FormatInt(ids[1], 10)
	zutaten[1].Name = "Vollmilch"

	trans, _ = btDB.Begin()
	type args struct {
		z     []Zutat
		trans *sql.Tx
	}
	tests := []struct {
		name string
		args args
		want bool
	}{
		// TODO: Add test cases.
		//TF129
		{name: "Zutat existiert",
			args: args{z: zutaten,
				trans: trans},
			want: true,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := UpdateZutaten(tt.args.z, tt.args.trans); got != tt.want {
				t.Errorf("UpdateZutaten() = %v, want %v", got, tt.want)
			}
		})
	}
	trans.Commit()
	EndTest()
}
