package model

import (
	"reflect"
	"strconv"
	"testing"

	_ "github.com/go-sql-driver/mysql"
)

func TestAddBeitragToDB(t *testing.T) {
	BeginnTest()
	trans, _ := btDB.Begin()
	_, err := trans.Exec("DELETE FROM `beitrag`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE beitrag AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	trans.Commit()
	beitrag := Beitrag{
		Inhalt:    "Test",
		Kategorie: "Lebensmittel",
		Titel:     "Test",
	}

	type args struct {
		b Beitrag
	}
	tests := []struct {
		name string
		args args
		want int64
	}{
		// TODO: Add test cases.
		//TF107
		{name: "Beitrag korrekt",
			args: args{b: beitrag},
			want: 1,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := AddBeitragToDB(tt.args.b); got != tt.want {
				t.Errorf("AddBeitragToDB() = %v, want %v", got, tt.want)
			}
		})
	}
	EndTest()
}

func TestDeleteBeitragById(t *testing.T) {
	BeginnTest()
	trans, _ := btDB.Begin()
	_, err := trans.Exec("DELETE FROM `beitrag`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE beitrag AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	trans.Commit()
	beitrag := Beitrag{
		Inhalt:    "Test",
		Kategorie: "Lebensmittel",
		Titel:     "Test",
	}
	id := AddBeitragToDB(beitrag)
	beitrag.BeitID = int(id)
	type args struct {
		id string
	}
	tests := []struct {
		name string
		args args
		want bool
	}{
		// TODO: Add test cases.
		//TF101
		{name: "Beitrag existiert",
			args: args{id: strconv.Itoa(beitrag.BeitID)},
			want: true},
		//TF102
		{name: "Beitrag existiert nicht",
			args: args{id: strconv.Itoa(0)},
			want: false},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := DeleteBeitragById(tt.args.id); got != tt.want {
				t.Errorf("DeleteBeitragById() = %v, want %v", got, tt.want)
			}
		})
	}
	EndTest()
}

func TestGetBeitragById(t *testing.T) {
	var nullBeitrag Beitrag
	BeginnTest()
	beitrag := Beitrag{
		Inhalt:    "Test",
		Kategorie: "Lebensmittel",
		Titel:     "Test",
	}
	id := AddBeitragToDB(beitrag)
	beitrag.BeitID = int(id)

	type args struct {
		id string
	}
	tests := []struct {
		name string
		args args
		want Beitrag
	}{
		// TODO: Add test cases.
		//TF103
		{name: "Beitrag existiert",
			args: args{id: strconv.Itoa(beitrag.BeitID)},
			want: beitrag},
		//TF104
		{name: "Beitrag existiert nicht",
			args: args{id: strconv.Itoa(0)},
			want: nullBeitrag},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := GetBeitragById(tt.args.id); !reflect.DeepEqual(got, tt.want) {
				t.Errorf("GetBeitragById() = %v, want %v", got, tt.want)
			}
		})
	}
	EndTest()
}

func TestUpdateBeitragToDB(t *testing.T) {
	BeginnTest()
	beitrag := Beitrag{
		Inhalt:    "Test",
		Kategorie: "Lebensmittel",
		Titel:     "Test",
	}
	id := AddBeitragToDB(beitrag)
	beitrag.BeitID = int(id)
	beitrag.Titel = "Test2"
	beitrag2 := Beitrag{
		BeitID:    0,
		Inhalt:    "Test",
		Kategorie: "Lebensmittel",
		Titel:     "Test",
	}
	type args struct {
		b Beitrag
	}
	tests := []struct {
		name string
		args args
		want bool
	}{
		// TODO: Add test cases.
		//TF105
		{name: "Beitrag existiert",
			args: args{b: beitrag},
			want: true},
		//TF106
		{name: "Beitrag existiert nicht",
			args: args{b: beitrag2},
			want: false},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := UpdateBeitragToDB(tt.args.b); got != tt.want {
				t.Errorf("UpdateBeitragToDB() = %v, want %v", got, tt.want)
			}
		})
	}
	EndTest()
}
