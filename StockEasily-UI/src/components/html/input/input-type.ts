export const enum InputType {
    Button = 'button',
    Checkbox = 'checkbox',
    Color = 'color',
    Date = 'date',
    DatetimeLocal = 'datetime-local',
    Email = 'email',
    File = 'file',
    Hidden = 'hidden',
    Image = 'image',
    Month = 'month',
    Number = 'number',
    Password = 'password',
    Radio = 'radio',
    Range = 'range',
    Reset = 'reset',
    Search = 'search',
    Submit = 'submit',
    Tel = 'tel',
    Text = 'text',
    Time = 'time',
    Url = 'url',
    Week = 'week',
    ExternalSelect = 'externalSelect'
}

export function containsMinMaxStep(type: InputType): boolean {
    return type === InputType.Number
        || type === InputType.Range
        || type === InputType.Date
        || type === InputType.DatetimeLocal
        || type === InputType.Month
        || type === InputType.Time
        || type === InputType.Week;
}

export function containsPlaceholder(type: InputType): boolean {
    return type === InputType.Text
        || type === InputType.Search
        || type === InputType.Url
        || type === InputType.Tel
        || type === InputType.Email
        || type === InputType.Password;
}
